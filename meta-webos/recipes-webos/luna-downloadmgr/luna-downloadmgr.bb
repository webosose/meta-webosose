# Copyright (c) 2012-2018 LG Electronics, Inc.

SUMMARY="The Download Manager service supports the downloading and uploading of files to and from a HP webOS device."
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "libpbnjson luna-service2 sqlite3 curl uriparser pmloglib jemalloc luna-prefs boost glib-2.0"
RDEPENDS_${PN} = "applicationinstallerutility"

WEBOS_VERSION = "4.0.0-1_6e05a03470d5a3dae67b893972c36d851b146c0f"
PR = "r10"

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
