# Copyright (c) 2020 LG Electronics, Inc.

SUMMARY = "Bluetooth HFP(Hands Free Profile) support service"
AUTHOR = "Sameer Mulla <sameer.mulla@lge.com>"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 glib-2.0-native luna-service2 pmloglib libpbnjson"

WEBOS_VERSION = "1.0.0-13_856083d65639f46c55ec238d9ae44788a3dd27cd"
PR = "r0"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_public_repo

# Set WEBOS_HFP_ENABLED_ROLE to a space-separted list of
# HFP (Hands Free Profile) role to be supported by the
# webOS HFP support service at runtime.
# Possible candidate of HFP role is AG(Audio Gateway),
# HF (Hands Free).
WEBOS_HFP_ENABLED_ROLE = "HF"

EXTRA_OECMAKE += "-DWEBOS_HFP_ENABLED_ROLE:STRING='${WEBOS_HFP_ENABLED_ROLE}'"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
