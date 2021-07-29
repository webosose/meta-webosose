# Copyright (c) 2017-2020 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos3"

PACKAGECONFIG:append:class-native = " virglrenderer glx spice libusb usb-redir"
PACKAGECONFIG:append:class-nativesdk = " virglrenderer glx spice libusb usb-redir"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
