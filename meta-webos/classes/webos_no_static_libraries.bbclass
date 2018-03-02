# Copyright (c) 2014-2017 LG Electronics, Inc.
#
# webos_no_static_libraries
#
# Do not stage or package any static libraries built by a component unless
# specific ones are assigned to WEBOS_NO_STATIC_LIBRARIES_WHITELIST. Other
# distro-s can allow usage of all of them by setting
# WEBOS_NO_STATIC_LIBRARIES_WHITELIST_forcevariable = "*" in DISTRO.conf .

#
# Add settings for upstream components here instead of creating .bbappends.
#

# Needed by numerous components (libc.a is needed for BDK to build lginit):
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-eglibc = "libc.a libc_nonshared.a libpthread_nonshared.a"
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-glibc = "libc.a libc_nonshared.a libpthread_nonshared.a"
# Needed by numerous components:
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-libgcc = "libgcc.a"
# Needed by busybox:
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-libgcc += "libgcc_eh.a"
# Needed by numerous components:
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-libgcc-initial = "libgcc.a"
# Needed by dhcp (svtest), libirs and libisccfg is needed by for 4.3.0 dhcp version:
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-bind = "libdns.a libisc.a libirs.a libisccfg.a"

# Needed by libpam (fails to find yywrap symbol)
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-flex = "libfl.a"

# Needed by ltrace:
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-elfutils = "libelf.a"
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-gcc-runtime = "libsupc++.a"
# Needed by perl:
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-gcc-runtime = "libssp_nonshared.a"
# Needed by jq:
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-onig = "libonig.a"
# Needed by oprofile:
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-binutils = "libiberty.a"
# Not needed yet (but plan for the future):
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_pn-cpputest = "libCppUTestExt.a"

# Needed by gummiboot
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_append_pn-gnu-efi = " libefi.a libgnuefi.a"
# Needed by expect
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_append_pn-tcl = " libtclstub8.6.a"
# Needed by tcpreplay
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_append_pn-libpcap = " libpcap.a"
# Needed by webappmanager3
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_append_pn-gmock = " libgmock.a libgmock_main.a"

# Usage: webos_no_static_libraries_find_static [<root-dir>]
webos_no_static_libraries_find_static() {
    # Do the cd so that we can return relative paths; the removals in
    # sysroot_stage_libdir_append() are done in a different tree.
    cd "$1"

    # When WEBOS_NO_STATIC_LIBRARIES_WHITELIST is '', the egrep -v '/()$'
    # works correctly and drops nothing because none of the paths
    # will end with / .
    # Need || true because if there's nothing output, the exit status will
    # be non-zero.
    find . -name '*.a' | \
        egrep -v '${@ "/(" + "|".join("${WEBOS_NO_STATIC_LIBRARIES_WHITELIST}".split()).replace('.', '[.]').replace('+', '[+]') + ')$'}' || true
}

WEBOS_NO_STATIC_LIBRARIES_WHITELIST ??= ""
# Always install the native and nativesdk static libraries since they are never
# used when linking target executables.
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_class-native = "*"
WEBOS_NO_STATIC_LIBRARIES_WHITELIST_class-nativesdk = "*"
sysroot_stage_libdir_prepend() {
    local staticlibs=""
    if [ '${WEBOS_NO_STATIC_LIBRARIES_WHITELIST}' != '*' ]; then
        # XXX pysh used in bitbake doesn't recognize the invocation of
        # webos_no_static_libraries_find_static if it's done inside of $( )
        # (or ` `) and therefore leaves its definition out of the run.* script
        # it generates, trick pysh to think it's executed here
        false && webos_no_static_libraries_find_static
        staticlibs=$(webos_no_static_libraries_find_static $1)
    fi
}

sysroot_stage_libdir_append() {
    if [ '${WEBOS_NO_STATIC_LIBRARIES_WHITELIST}' != '*' -a -n "$staticlibs" ]; then
        if [ '${WEBOS_NO_STATIC_LIBRARIES_WHITELIST}' = '' ]; then
            bbnote "Removing all staged static libraries:"
        else
            bbnote "Removing all staged static libraries except for '${WEBOS_NO_STATIC_LIBRARIES_WHITELIST}':"
        fi
        cd "$2"
        rm -vf $staticlibs
        cd - > /dev/null
    fi
    # Prevent non-zero status from being returned when the if test fails
    true
}

PACKAGESPLITFUNCS += "webos_no_static_libraries_depopulate_staticdev"

webos_no_static_libraries_depopulate_staticdev() {
    if [ '${WEBOS_NO_STATIC_LIBRARIES_WHITELIST}' != '*' ]; then
        local staticdevdir
        for staticdevdir in $(find ${PKGDEST} -maxdepth 1 -type d -name '*-staticdev'); do
            local staticlibs
            # XXX pysh used in bitbake doesn't recognize the invocation of
            # webos_no_static_libraries_find_static if it's done inside of $( )
            # (or ` `) and therefore leaves its definition out of the run.* script
            # it generates, trick pysh to think it's executed here
            false && webos_no_static_libraries_find_static
            staticlibs=$(webos_no_static_libraries_find_static $staticdevdir)
            if [ -n "$staticlibs" ]; then
                local pkgname
                pkgname=$(basename $staticdevdir)
                if [ '${WEBOS_NO_STATIC_LIBRARIES_WHITELIST}' = '' ]; then
                    bbnote "Removing all static libraries from $pkgname:"
                else
                    bbnote "Removing all static libraries from $pkgname except for '${WEBOS_NO_STATIC_LIBRARIES_WHITELIST}':"
                fi
                cd $staticdevdir
                rm -vf $staticlibs
                # If all that remains are empty directories, remove them so that the
                # package will be empty
                if [ -z "$(find . ! -type d)" ]; then
                    rm -vrf $(ls -A)
                fi
                cd - > /dev/null
           fi
        done
    fi
    # Prevent non-zero status from being returned when the if test fails
    true
}
