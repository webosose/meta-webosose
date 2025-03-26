# Copyright (c) 2020-2025 LG Electronics, Inc.

SUMMARY = "Mediacontroller service"
AUTHOR = "Sujeet Nayak <Sujeet.nayak@lge.com>"
SECTION = "webos/services"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 luna-service2 json-c pmloglib curl"

WEBOS_VERSION = "1.0.0-41_f85e0b7caa8a92ede08e13db56827ca20cd4ff71"
PR = "r9"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_system_bus
inherit webos_daemon

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "com.webos.service.mediacontroller.service"

FILES:${PN} += "${webos_testsdir}/${BPN}"
