# Copyright (c) 2013-2019 LG Electronics, Inc.

SUMMARY = "webOS uMediaserver daemon and utilities"
AUTHOR = "Ian Cain <ian.cain@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0 libpbnjson libconfig swig-native libxml2 python luna-service2 pmloglib boost luna-prefs"
DEPENDS += "gstreamer1.0 gstreamer1.0-plugins-base"
DEPENDS += "${@'' if '${WEBOS_DISTRO_PRERELEASE}' == '' else 'pmtrace'}"
RDEPENDS_${PN} = "umediaserver-configs"

WEBOS_VERSION = "1.0.0-16_ed4626db2beb8722d7934eec49e5eaccccf5747f"
PR = "r10"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_daemon
inherit webos_system_bus
inherit webos_machine_dep
inherit python-dir
inherit pythonnative
inherit webos_public_repo

# umediaserver doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGECONFIG = "com.webos.service.videooutput"
PACKAGECONFIG[com.webos.service.videooutput] = "-DUSE_VIDEOOUTPUTD:BOOL=TRUE,-DUSE_VIDEOOUTPUTD:BOOL=FALSE,,com.webos.service.videooutput"

# umediaserver-python contains the Python bindings
PACKAGES =+ "${PN}-python"

FILES_${PN}-python = "${libdir}/${PYTHON_DIR}/site-packages/uMediaServer/* ${datadir}/${BPN}/python/"
