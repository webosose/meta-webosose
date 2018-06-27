# Copyright (c) 2017-2018 LG Electronics, Inc.

SUMMARY = "Service which controls audio and video output"
AUTHOR = "Gayathri Srinivasan <gayathri.srinivasan@lge.com>"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

VIRTUAL-RUNTIME_aval-impl ??= "avoutput-adaptation-layer-mock"
DEPENDS = "glib-2.0 luna-service2 pmloglib libpbnjson aval-impl"
RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_aval-impl}"
WEBOS_VERSION = "1.0.0-2_e299c7b19d80be16126f04934cb93b0d9e1111c4"
PR = "r0"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_distro_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
