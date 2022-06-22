# Copyright (c) 2012-2022 LG Electronics, Inc.

SUMMARY="The Download Manager service supports the downloading and uploading of files to and from a HP webOS device."
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/apps"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2714381d01eb5a6e963e62a212e277be \
"

DEPENDS = "libpbnjson luna-service2 sqlite3 curl uriparser pmloglib jemalloc luna-prefs boost glib-2.0"
RDEPENDS:${PN} = "applicationinstallerutility"

WEBOS_VERSION = "4.0.0-11_0d0d19e810ade0dff8443daa4f0c845b40662b93"
PR = "r11"

inherit webos_component
inherit webos_library
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_machine_dep
inherit webos_public_repo

WEBOS_MACHINE ?= "${MACHINE}"
EXTRA_OECMAKE += "-DMACHINE=${WEBOS_MACHINE}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
