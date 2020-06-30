# Copyright (c) 2020 LG Electronics, Inc.

SUMMARY = "Mediacontroller service"
AUTHOR = "Sapna Kumari"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 luna-service2 json-c pmloglib"

WEBOS_VERSION = "1.0.0-9_bc07f05fa90a19ec14c6fa6456c3cd6ae099df25"
PR = "r1"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_system_bus
inherit webos_daemon

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "^qemux86$|^raspberrypi4$|^raspberrypi4-64$"

FILES_${PN} += "${libdir}/*.so"
FILES_SOLIBSDEV = ""
INSANE_SKIP_${PN} += "dev-so"
