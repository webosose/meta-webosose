# Copyright (c) 2017-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

PACKAGECONFIG_append_class-native = " virglrenderer glx spice libusb usb-redir"
PACKAGECONFIG_append_class-nativesdk = " virglrenderer glx spice libusb usb-redir"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
