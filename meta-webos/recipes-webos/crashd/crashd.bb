# Copyright (c) 2013-2020 LG Electronics, Inc.

SUMMARY = "Crash reporting daemon"
AUTHOR = "Ed Chejlava <ed.chejlava@lge.com>"
SECTION = "base"
LICENSE = "LGPL-2.1+ & Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://src/libSegFault/libSegFault.c.in;endline=20;md5=4c8f61c043fd7c3b78b738ca09bcfc35 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

PNLIBSEGFAULT = "libsegfault-webos"
SUMMARY_${PNLIBSEGFAULT} = "webOS edition of libSegFault.so that is universally preloaded"

DEPENDS = "librdx pmloglib glib-2.0 libunwind"
PROVIDES = "libsegfault"
RDEPENDS_${PN} = "${PNLIBSEGFAULT} gzip"
RPROVIDES_${PNLIBSEGFAULT} = "libsegfault"
RREPLACES_${PNLIBSEGFAULT} = "libsegfault"
RCONFLICTS_${PNLIBSEGFAULT} = "libsegfault"

WEBOS_VERSION = "1.2.5-4_bfc3dd73a27b474eaf6051bb358a40bd1593ddc2"
PR = "r12"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_filesystem_paths
inherit webos_public_repo
inherit webos_prerelease_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DWEBOS_INSTALL_CRASHDDIR:STRING=${webos_crashddir}"

# Generate core dumps on non-production builds.
EXTRA_OECMAKE += "-DENABLE_CORE_DUMP:STRING=${@bb.utils.contains('WEBOS_DISTRO_PRERELEASE', 'devel', '1', '0', d)}"

# webos_build_library() from cmake-modules-webos 1.0.0 RC4 doesn't support
# installation into ${base_libdir}. It also doesn't support building unversioned
# libraries, which libSegFault.so should be. Fix up the filename name so that it
# doesn't look like it's versioned, even though its SONAME is (benignly).
do_install_append() {
    # If a versioned library has been built, libSegFault.so will always be a symlink
    if [ -L ${D}${libdir}/libSegFault.so ]; then
        rm -vf ${D}${libdir}/libSegFault.so
        mv -v ${D}${libdir}/libSegFault.so.*.*.* ${D}${libdir}/libSegFault.so
        rm -vf ${D}${libdir}/libSegFault.so.*
    fi
    # don't report fail (when using 'usrmerge' DISTRO_FEATURE, lib dir is already on root_prefix)
    mv -v ${D}${libdir} ${D}${root_prefix} 2> /dev/null || true
}

# Process PNLIBSEGFAULT first
PACKAGES =+ "${PNLIBSEGFAULT}"
# Without this, the package is named libsegfault1. Note that libSegFault.so is
# always unversioned.
DEBIANNAME_${PNLIBSEGFAULT} = "${PNLIBSEGFAULT}"

# In order to keep in sync with glibc's libSegFault.so, an unversioned
# libSegFault.so (without a SONAME) symlink needs to go into FILES_${PNLIBSEGFAULT}
FILES_${PNLIBSEGFAULT} = "${base_libdir}/*.so*"
FILES_SOLIBSDEV = ""

# Disable QA insanity checks that don't apply to this component
#
# dev-so: Suppress error when *.so is not a link to versioned equivalent
INSANE_SKIP_${PNLIBSEGFAULT} = "dev-so"

pkg_postinst_${PNLIBSEGFAULT}() {
    if [ -e $D${sysconfdir}/ld.so.preload -a -s $D${sysconfdir}/ld.so.preload ]; then
        # remove old libSegFault entries
        sed -i '/libSegFault/d' $D${sysconfdir}/ld.so.preload
        if [ -s $D${sysconfdir}/ld.so.preload ]; then
            # append new libSegFault.so entry
            sed -i "$ a\
${libdir}/libSegFault.so" $D${sysconfdir}/ld.so.preload
        else
            echo "${base_libdir}/libSegFault.so" > $D${sysconfdir}/ld.so.preload
        fi
    else
        # When building bdk, ${sysconfdir} might not exist in the sysroot
        mkdir -p $D${sysconfdir}
        echo "${base_libdir}/libSegFault.so" > $D${sysconfdir}/ld.so.preload
    fi
}

pkg_prerm_${PNLIBSEGFAULT}() {
    # remove libSegFault entry from /etc/ld.so.preload
    sed -i '/libSegFault/d' $D${sysconfdir}/ld.so.preload
    if [ ! -s $D${sysconfdir}/ld.so.preload ]; then
        rm -f $D${sysconfdir}/ld.so.preload
    fi
}
