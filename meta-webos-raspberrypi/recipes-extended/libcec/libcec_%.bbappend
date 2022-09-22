# Copyright (c) 2017-2022 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi3"

# The recipe in meta-oe already has conditional dependency:
# DEPENDS:append:rpi = "${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', '', ' userland', d)}"
# but in our builds the userland is used even when vc4graphics is in MACHINE_FEATURES
DEPENDS:append:rpi = " virtual/libomxil"

FILESEXTRAPATHS:prepend:rpi := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " file://0001-Fix-MessageReceived.patch"
