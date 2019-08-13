# Copyright (c) 2019 LG Electronics, Inc.

SUMMARY = "Camera service framework to control camera devices"
AUTHOR = "Gururaj Patil"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 luna-service2 json-c alsa-lib pmloglib udev"

WEBOS_VERSION = "1.0.0-4_7cc2eada0260ae238227c840d774587294e7bfab"
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

COMPATIBLE_MACHINE = "^raspberrypi3$"

# Build for raspberrypi4
COMPATIBLE_MACHINE_append = "|^raspberrypi4$"

FILES_${PN} += "${libdir}/*.so"
FILES_SOLIBSDEV = ""
INSANE_SKIP_${PN} += "dev-so"
