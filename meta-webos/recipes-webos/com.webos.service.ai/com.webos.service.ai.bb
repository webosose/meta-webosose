# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "Ai service for voice/face/gesture recognition"
AUTHOR = "Kyungjik Min <dp.min@lge.com>"
SECTION = "webos/extended-service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "glib-2.0 luna-service2 json-c pmloglib libgoogleassistant"

# libgoogleassistant contains prebuilt binaries useful only on raspberrypi3
COMPATIBLE_MACHINE = "^raspberrypi3$"
COMPATIBLE_MACHINE_raspberrypi3-64 = "^$"

WEBOS_VERSION = "1.0.0-3_2b2b37d8000d5a213ea7fc8f081fdd54b345fec8"
PR = "r0"

inherit systemd
inherit webos_public_repo
inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install_append() {
    if [ ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', True, False, d)} ]; then
        install -d ${D}${systemd_system_unitdir}
        install -d ${D}/var/systemd/system/env
        install -m 644 ${S}/files/systemd/services/ai.service ${D}${systemd_system_unitdir}/
        install -m 644 ${S}/files/systemd/services/ai.env ${D}/var/systemd/system/env/
    fi
}

SYSTEMD_SERVICE_${PN} = "ai.service"
