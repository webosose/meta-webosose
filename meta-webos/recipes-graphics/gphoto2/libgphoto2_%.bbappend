# Copyright (c) 2017-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

PACKAGES =+ "${PN}-print-camera-list"
FILES:${PN}-print-camera-list = "${libdir}/*/print-camera-list"

PACKAGES =+ "${PN}-gpl"
FILES:${PN}-gpl = "${libdir}/libgphoto2*/*/pentax.so"
