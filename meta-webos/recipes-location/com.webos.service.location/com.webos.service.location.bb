# Copyright (c) 2020-2023 LG Electronics, Inc.

DESCRIPTION = "location framework which provides location based services implementing location handlers, plugins, and Luna location service"
AUTHOR = "Muralidhar N <muralidhar.n@lge.com>"
SECTION = "webos/location"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
    file://oss-pkg-info.yaml;md5=7be9908f876cc5f1edaf1124d0084067 \
"

DEPENDS = "glib-2.0 libpbnjson libxml2 pmloglib luna-service2 luna-prefs loc-utils boost"

WEBOS_VERSION = "1.0.0-104_69e22e6c07d087096885809b3f0da5650af3a488"
PR = "r4"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_program
inherit webos_system_bus
inherit webos_machine_impl_dep

# http://gpro.lge.com/c/webosose/com.webos.service.location/+/348157 Fix luna-service2 usage
# http://gpro.lge.com/c/webosose/com.webos.service.location/+/350819 CMakeLists.txt: Fix hardcoded 'lib' installation path
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
"
S = "${WORKDIR}/git"

FILES:${PN} += "${libdir}/location/plugins/lib*.so"
SECURITY_STRINGFORMAT = ""
