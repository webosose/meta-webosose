# Copyright (c) 2013-2019 LG Electronics, Inc.

SUMMARY = "Remote diagnostics daemon and utilities"
AUTHOR = "Gayathri Srinivasan <gayathri.srinivasan@lge.com>"
SECTION = "base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 libpbnjson luna-prefs luna-service2 pmloglib"
#Add tar dependency since --absolute-names support is missing in busybox tar
RDEPENDS_${PN} = "nyx-utils tar"

WEBOS_VERSION = "4.0.2-8_9cbb90a2ce560219c286a9d99cd2e1c5bdf8bc64"
PR = "r8"

PROVIDES = "librdx rdx-utils"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_library
inherit webos_distro_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DWEBOS_USE_LEGACY_PACKAGE_MANAGER:BOOL=FALSE"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
