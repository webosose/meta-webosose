# Copyright (c) 2017-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi6"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target:rpi = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target:rpi = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# We use vc4graphics so need libegl-mesa provider for:
# RDEPENDS:${PN} += "${@bb.utils.contains("MACHINE_FEATURES", "vc4graphics", "libegl-mesa", "", d)}"
inherit features_check
ANY_OF_DISTRO_FEATURES:rpi = "vulkan opengl"
