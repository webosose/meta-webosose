# Copyright (c) 2019-2023 LG Electronics, Inc.

SUMMARY = "Camera service framework to control camera devices"
AUTHOR = "Kalaimani K <kalaimani.k@lge.com>"
SECTION = "webos/services"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 luna-service2 json-c alsa-lib pmloglib udev"

WEBOS_VERSION = "1.0.0-35_8ec5e64470df87173c8013e7661d96541c6d544c"
PR = "r8"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_system_bus
inherit webos_daemon

# depends on edgeai-vision
PACKAGECONFIG ??= " \
    ${@bb.utils.filter('DISTRO_FEATURES', 'webos-aiframework', d)}\
"

PACKAGECONFIG[webos-aiframework] = "-DWITH_AIFRAMEWORK=ON,-DWITH_AIFRAMEWORK=OFF,edgeai-vision"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES:${PN} += "${libdir}/*.so"
# The libv4l2-camera-plugin.so symlink is actually used in runtime now and widthout it com.webos.service.camera2 fails to find it with:
# raspberrypi4-64 com.webos.service.camera2[1253]: [] [pmlog] HAL HAL {} camera_hal_if_init():42 dlopen failed for : libv4l2-camera-plugin.so
# The code should be changed to open libv4l2-camera-plugin.so.1 instead to resolve ABI incompatibility.
# Building versioned libraries and then using them through unversioned symlink is common mistake.
FILES_SOLIBSDEV = ""
INSANE_SKIP:${PN} += "dev-so"
