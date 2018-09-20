# Copyright (c) 2017-2019 LG Electronics, Inc.

SUMMARY = "Application Install Service"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 luna-service2 libpbnjson pmloglib pmtrace boost icu"
RDEPENDS_${PN} = "applicationinstallerutility ecryptfs-utils librolegen"

WEBOS_VERSION = "1.0.0-2_53225020493e53d8714a2a73f0bc56e43a1870c9"
PR = "r2"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_daemon
inherit webos_system_bus
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

SRC_URI += "file://0001-CMakeLists.txt-don-t-check-for-signals-Boost-library.patch"
