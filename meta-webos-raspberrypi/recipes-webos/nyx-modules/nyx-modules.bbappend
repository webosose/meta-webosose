# Copyright (c) 2020-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi8"

RDEPENDS:${PN}:append:rpi = "${@bb.utils.contains_any('DISTRO_FEATURES', 'vulkan opengl', ' libcec-examples', '', d)}"

NYX_MODULES_REQUIRED:append:rpi = "${@bb.utils.contains_any('DISTRO_FEATURES', 'vulkan opengl', 'NYXMOD_OW_CEC;', '', d)}"
