# Copyright (c) 2023 LG Electronics, Inc.
SUMMARY = "Media Recorder Service"
AUTHOR = "Sungho Lee <shl.lee@lge.com>"
SECTION = "webos/services"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 luna-service2 pmloglib nlohmann-json"

WEBOS_VERSION = "1.0.0-1_ab3652ecfc70c53ee79a660752ebbd57e9502804"
PR = "r0"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_system_bus
inherit webos_daemon

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "com.webos.service.mediarecorder.service"

# Build a native app for testing the media recorder
PACKAGECONFIG[test-apps] = "-DWITH_CAMERA_TEST=ON,-DWITH_CAMERA_TEST=OFF, webos-wayland-extensions mesa jpeg, ${PN}-test-apps"

PACKAGES += "${PN}-test-apps"

RDEPENDS:${PN}-test-apps = "${PN}"

FILES:${PN}-test-apps = "${webos_applicationsdir}"
