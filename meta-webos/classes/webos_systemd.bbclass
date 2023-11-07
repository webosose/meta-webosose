# Copyright (c) 2023 LG Electronics, Inc.
#
# webos_systemd
#
# This class is to be inherited by the recipe for every component which has systemd units like *.{service,path,target,sh}
#

inherit systemd

FILESEXTRAPATHS:prepend := "${META_WEBOS_LAYER}/files:"
SRC_URI:append = " \
    file://replace.cmake \
    ${@' '.join(['file://' + f for f in '${WEBOS_SYSTEMD_SERVICE}'.split()])} \
    ${@' '.join(['file://' + f for f in '${WEBOS_SYSTEMD_SCRIPT}'.split()])} \
"

WEBOS_SYSTEMD_SERVICE ?= ""
WEBOS_SYSTEMD_SCRIPT ?= ""

def removesuffix(text, suffix):
    if text.endswith(suffix):
        return text[:-len(suffix)]
    else:
        return text

SYSTEMD_SERVICE:${PN} = "${@' '.join([removesuffix(f, '.in') for f in '${WEBOS_SYSTEMD_SERVICE}'.split()])}"

SYSTEMD_AUTO_ENABLE = "disable"

# User and group will be set as root if webos-dac is not in distro features.
WEBOS_SYSTEMD_USER ?= "root"
WEBOS_SYSTEMD_GROUP ?= "root"

install_units() {
    install -d ${WORKDIR}/staging-units

    for f in ${WEBOS_SYSTEMD_SERVICE} ${WEBOS_SYSTEMD_SCRIPT}; do
        cp ${WORKDIR}/$f ${WORKDIR}/staging-units/
    done

    if [ $(ls ${WORKDIR}/staging-units | wc -l) -gt 0 ]; then
        ls ${WORKDIR}/replace.cmake >/dev/null 2>/dev/null && cp ${WORKDIR}/replace.cmake ${WORKDIR}/staging-units/CMakeLists.txt
        (cd ${WORKDIR} && \
         cmake staging-units \
                 -DIN_FILES="${WEBOS_SYSTEMD_SERVICE} ${WEBOS_SYSTEMD_SCRIPT}" \
                 -DCMAKE_INSTALL_UNITDIR="${D}${systemd_system_unitdir}" \
                 -DWEBOS_SYSTEMD_USER="${@bb.utils.contains('DISTRO_FEATURES', 'webos-dac', '${WEBOS_SYSTEMD_USER}', 'root', d)}" \
                 -DWEBOS_SYSTEMD_GROUP="${@bb.utils.contains('DISTRO_FEATURES', 'webos-dac', '${WEBOS_SYSTEMD_GROUP}', 'root', d)}" && \
         make install)
    fi

    rm -rf ${WORKDIR}/staging-units
}

do_install[postfuncs] += "install_units"
do_install[depends] += "cmake-native:do_populate_sysroot cmake-modules-webos-native:do_populate_sysroot"

FILES:${PN} += "${systemd_system_unitdir}/*"
