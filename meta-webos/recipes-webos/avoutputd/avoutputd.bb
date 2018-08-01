# Copyright (c) 2017-2018 LG Electronics, Inc.

SUMMARY = "Service which controls audio and video output"
AUTHOR = "Gayathri Srinivasan <gayathri.srinivasan@lge.com>"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

VIRTUAL-RUNTIME_aval-impl ??= "avoutput-adaptation-layer-mock"
DEPENDS = "glib-2.0 luna-service2 pmloglib libpbnjson aval-impl"
RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_aval-impl}"
WEBOS_VERSION = "1.0.0-5_36d08c75feb62391af087ccdc9c00fcaf8271712"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_distro_dep
inherit webos_public_repo
inherit webos_machine_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
