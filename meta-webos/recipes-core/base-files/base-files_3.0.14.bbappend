# Copyright (c) 2013-2025 LG Electronics, Inc.

AUTHOR = "Herb Kuta <herb.kuta@lge.com>"

inherit webos_filesystem_paths
inherit webos_machine_impl_dep
inherit webos_prerelease_dep

EXTENDPRAUTO:append = "webos18"

BASEFILESISSUEINSTALL = ""

dirs700 = " \
    ${webos_db8datadir}/temp \
    ${webos_db8mediadir} \
"

dirs755 += " \
    ${webos_db8datadir} \
    ${webos_cryptofsdir} \
    ${webos_systemdir} \
    ${webos_preferencesdir} \
    ${webos_optdir} \
"

# webOS expects this directory to be writeable by all (because it's typically
# been mounted on a VFAT partition, which doesn't enforce permissions).
dirs777 = " \
    ${webos_mountablestoragedir} \
"

do_install:prepend() {
    local d
    for d in ${dirs700}; do
        install -v -m 0700 -d ${D}$d
    done
    for d in ${dirs777}; do
        install -v -m 0777 -d ${D}$d
    done
    if [ "${webos_optdir}" != "/opt" ] ; then
        ln -snf ${webos_optdir} ${D}/opt
    fi
}

do_install:append() {
    # additional entries for fstab
    bbnote "Adding entries to ${sysconfdir}/fstab"
    generate_fstab_entries >> ${D}${sysconfdir}/fstab

    bbnote "Ensuring that fstab has exactly one record per mount-point"
    local collisions
    collisions=$(awk '
        { gsub(/\s*(#.*)?$/,"") }
        /^$/ { next }
        ++t[$2] == 2 { printf "%s ", $2 }
        ' ${D}${sysconfdir}/fstab)

    [ -z "$collisions" ] \
        || bbfatal "Found records in fstab with identical mount-points: $collisions"
}

do_install:append:hardware() {
    # For coredump handling
    if ${@oe.utils.conditional('WEBOS_DISTRO_PRERELEASE', 'devel', 'true', 'false', d)}; then
        echo "" >> ${D}${sysconfdir}/profile
        echo "# Set limit of core file size" >> ${D}${sysconfdir}/profile
        echo "if [ $(id -u) -eq 0 ]; then" >> ${D}${sysconfdir}/profile
        echo "    ulimit -c unlimited" >> ${D}${sysconfdir}/profile
        echo "fi" >> ${D}${sysconfdir}/profile
    fi
}

generate_fstab_entries() {
    echo "# additional in-memory storage for db8"
    echo "tmpfs ${webos_db8datadir}/temp tmpfs size=80M,mode=0700 0 0"
}

PR:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"
do_install[postfuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'set_tmpfs_star', '', d)}"

set_tmpfs_star () {
    fstab="${D}/${sysconfdir}/fstab"
    awk '$1 == "tmpfs" {$4=$4",smackfsroot=*"} {print}' $fstab > "$fstab.tmp" && mv $fstab.tmp $fstab
}
