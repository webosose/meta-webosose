# Copyright (c) 2013-2023 LG Electronics, Inc.

# webOS system should be ready to have read-only /etc folder
# thus we move timezone/localtime to other place (in volatile partition)

EXTENDPRAUTO:append = "webos8"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://etcetera.patch"

inherit webos_filesystem_paths

# "slim" is the default since 2020b and used by oe-core recipe
# "fat" is needed by e.g. MariaDB's mysql_tzinfo_to_sql
ZIC_FMT = "fat"

do_install:append() {
    install -d "${D}/${webos_sysmgr_localstatedir}/preferences"
    install -d "${D}/${webos_sysmgr_localstatedir}/preferences/zoneinfo/Etc"

    # move what was installed with DEFAULT_TIMEZONE
    mv "${D}${sysconfdir}/timezone" "${D}${webos_sysmgr_localstatedir}/preferences/"
    mv "${D}${sysconfdir}/localtime" "${D}${webos_sysmgr_localstatedir}/preferences/"

    # Copy what was installed with DEFAULT_TIMEZONE
    cp "${D}${datadir}/zoneinfo/Universal" "${D}/${webos_sysmgr_localstatedir}/preferences/zoneinfo/Etc/Manual"

    # make symlinks
    ln -s "${webos_sysmgr_localstatedir}/preferences/timezone" "${D}${sysconfdir}/"
    ln -s "${webos_sysmgr_localstatedir}/preferences/localtime" "${D}${sysconfdir}/"
    ln -s "${webos_sysmgr_localstatedir}/preferences/zoneinfo/Etc/Manual" "${D}${datadir}/zoneinfo/Etc/Manual"
}

# we replace current OE variant with our own based on original code
# please keep it in sync original OE variant or find a better solution
pkg_postinst:${PN}() {
    etc_lt=${LOCALTIME_LOCATION:-${webos_sysmgr_localstatedir}/preferences/localtime}
    src=${TIMEZONE_LOCATION:-${webos_sysmgr_localstatedir}/preferences/timezone}

    if [ -e $D${src} ] ; then
        tz=$(sed -e 's:#.*::' -e 's:[[:space:]]*::g' -e '/^$/d' "$D${src}")
    fi

    if [ -z ${tz} ] ; then
        exit 0
    fi

    if [ ! -e "$D${datadir}/zoneinfo/${tz}" ] ; then
        echo "You have an invalid TIMEZONE setting in ${src}"
        echo "Your ${etc_lt} has been reset to Universal; enjoy!"
        tz="Universal"
        echo "Updating $D${etc_lt} with $D${datadir}/zoneinfo/${tz}"
        ln -snf "${datadir}/zoneinfo/${tz}" "$D${etc_lt}"
    fi
}

FILES:${PN} += "${webos_sysmgr_localstatedir}/preferences"
FILES:${PN} += "${datadir}/zoneinfo/Etc/Manual"
