# Copyright (c) 2019-2024 LG Electronics, Inc.

SUMMARY = "Camera service framework to control camera devices"
AUTHOR = "Sungho Lee <shl.lee@lge.com>"
SECTION = "webos/services"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 luna-service2 json-c alsa-lib pmloglib udev nlohmann-json"

WEBOS_VERSION = "1.0.0-47_a6cc82ed43e7b563e0befc2a45905b5b967e0703"
PR = "r13"

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

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "com.webos.service.camera.service"
