# Copyright (c) 2012-2024 LG Electronics, Inc.

SUMMARY = "Palm's Better Native JSON library"
AUTHOR = "Yogish S <yogish.s@lge.com>"
SECTION = "webos/libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "yajl glib-2.0 gperf-native flex-native lemon-native gmp uriparser boost"

WEBOS_VERSION = "2.15.0-22_4536901c97aac54284b922b6475ab9cec691cbe4"
PR = "r18"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library

PACKAGECONFIG ??= ""
PACKAGECONFIG:append:class-native = " tools"

# These are the defaults, but explicitly specify so that readers know they exist
EXTRA_OECMAKE += "-DWITH_DOCS:BOOL=FALSE -DWITH_TESTS:BOOL=FALSE -DNO_LOGGING:BOOL=TRUE"
# Don't use CMake detection mechanisms for AR (it fails for class-native)
EXTRA_OECMAKE += "-DCMAKE_AR:FILEPATH=${AR}"

PACKAGECONFIG[tools] = "-DPBNJSON_INSTALL_TOOLS:BOOL=TRUE,-DPBNJSON_INSTALL_TOOLS:BOOL=FALSE"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

BBCLASSEXTEND = "native"
