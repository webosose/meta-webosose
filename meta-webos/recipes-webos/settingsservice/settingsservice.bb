# Copyright (c) 2013-2019 LG Electronics, Inc.

SUMMARY = "Settings Service"
AUTHOR = "Radhika S <radhika.s@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 glibmm luna-service2 libpbnjson pmloglib openssl libbson boost"
RDEPENDS_${PN} = "settingsservice-conf python"

WEBOS_VERSION = "1.0.22-3_fc48b9406a98316588007c157b5aa105b07365fd"
PR = "r22"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

WEBOS_SYSTEM_BUS_MANIFEST_TYPE = "SERVICE"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
