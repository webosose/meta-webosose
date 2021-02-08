# Copyright (c) 2013-2021 LG Electronics, Inc.

SUMMARY = "webOS uMediaserver daemon and utilities"
AUTHOR = "Ian Cain <ian.cain@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
LIC_FILES_CHKSUM_raspberrypi4 += "file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519"

DEPENDS = "glib-2.0 libpbnjson libconfig swig-native libxml2 luna-service2 pmloglib boost luna-prefs"
DEPENDS += "gstreamer1.0 gstreamer1.0-plugins-base"
DEPENDS += "${@'' if '${WEBOS_DISTRO_PRERELEASE}' == '' else 'pmtrace'}"
RDEPENDS_${PN} = "umediaserver-configs"

WEBOS_VERSION = "1.0.0-24_0407a711cbe8e747dfda50633d8b1c70b0ffc9ef"
PR = "r16"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_daemon
inherit webos_system_bus
inherit webos_machine_dep
inherit python3-dir
inherit python3native
inherit webos_public_repo
inherit webos_prerelease_dep

do_configure_prepend() {
    sed -i 's@add_subdirectory(test/python)@#disabled until updated to work with python3 add_subdirectory(test/python)@g' ${S}/CMakeLists.txt
}

# umediaserver doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"

WEBOS_GIT_PARAM_BRANCH_raspberrypi4 = "@gav"
WEBOS_VERSION_raspberrypi4 = "1.0.0-24.gav.17_41415127e55ba4932252cae0dc5accc13ff32c67"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGECONFIG = "com.webos.service.videooutput"
PACKAGECONFIG[com.webos.service.videooutput] = "-DUSE_VIDEOOUTPUTD:BOOL=TRUE,-DUSE_VIDEOOUTPUTD:BOOL=FALSE,,com.webos.service.videooutput"

# umediaserver-python contains the Python bindings
PACKAGES =+ "${PN}-python"

FILES_${PN}-python = "${libdir}/${PYTHON_DIR}/site-packages/uMediaServer/* ${datadir}/${BPN}/python/"

# needs to be fixed first
# http://caprica.lgsvl.com:8080/Errors/Details/1092075
# 1.0.0-184.open.10-r10/git/src/logger/Logger_macro.h:186:38: error: format not a string literal and no format arguments [-Werror=format-security]
#   char message[MAX_FT_SIZE]; snprintf(message, MAX_FT_SIZE, format, args...);
#                              ~~~~~~~~^~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
SECURITY_STRINGFORMAT = ""

SRC_URI += "file://0001-Fix-build-with-boost-1.73.0.patch"

#Remove videooutputd from OSE
PACKAGECONFIG_remove_raspberrypi4 = "com.webos.service.videooutput"
