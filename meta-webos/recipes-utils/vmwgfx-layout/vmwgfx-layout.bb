# Copyright (c) 2019-2025 LG Electronics, Inc.

SUMMARY = "Simple setlayout command tool for vmwgfx drm driver"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c75985e733726beaba57bc5253e96d04"

COMPATIBLE_MACHINE = "(qemux86|qemux86-64)"

PV = "0.1+git${SRCPV}"
PR = "r0"
SRCREV = "8e43168d508abc2702a97f8b264ab08330465608"

inherit systemd
inherit cmake

SRC_URI = "git://github.com/sparkleholic/vmwgfx-layout.git;branch=master;protocol=https \
    file://0001-Set-2-outputs-to-call-DRM_IOCTL_VMW_UPDATE_LAYOUT-fo.patch \
    file://0003-CMakeLists.txt-use-systemdsystemunitdir-instead-of-l.patch \
"
S = "${WORKDIR}/git"

EXTRA_OECMAKE = "-Dsystemdsystemunitdir=${systemd_system_unitdir}"

SYSTEMD_SERVICE:${PN} = "vmwgfx-setlayout.service"

FILES:${PN} = "${bindir} \
    ${systemd_system_unitdir} \
"
