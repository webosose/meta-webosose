# Copyright (c) 2012-2024 LG Electronics, Inc.

SUMMARY = "webOS portability layer - ${MACHINE}-specific modules"
AUTHOR = "Yogish S <yogish.s@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "nyx-lib glib-2.0 luna-service2 openssl udev nmeaparser"

RDEPENDS:${PN} = "lsb-release gzip"

WEBOS_VERSION = "7.1.0-22_677ed3eca696f1d5b00d8b7579f981e37e319ce3"
PR = "r19"

EXTRA_OECMAKE += "\
    -DDISTRO_VERSION:STRING='${DISTRO_VERSION}' \
    -DDISTRO_NAME:STRING='${DISTRO_NAME}${WEBOS_DISTRO_NAME_SUFFIX}' \
    -DWEBOS_DISTRO_RELEASE_PLATFORMCODE:STRING='${WEBOS_DISTRO_RELEASE_PLATFORMCODE}' \
    -DWEBOS_DISTRO_RELEASE_CODENAME:STRING='${WEBOS_DISTRO_RELEASE_CODENAME}' \
    -DWEBOS_DISTRO_BUILD_ID:STRING='${WEBOS_DISTRO_BUILD_ID}' \
"

# Only pass in a value for the Manufacturing version if one is actually
# defined. Otherwise, let the CMake script provide the default value.
#
# Defining it to be the empty string will override the default used in
# the CMake script.
WEBOS_DISTRO_MANUFACTURING_VERSION ??= ""
EXTRA_OECMAKE += "${@ '-DWEBOS_DISTRO_MANUFACTURING_VERSION:STRING="${WEBOS_DISTRO_MANUFACTURING_VERSION}"' \
                  if d.getVar('WEBOS_DISTRO_MANUFACTURING_VERSION') != '' else ''}"

# NB. CMakeLists.txt arranges for the return value of the NYX_OS_INFO_WEBOS_PRERELEASE
# query to be "" when WEBOS_DISTRO_PRERELEASE is not defined on the command line.
EXTRA_OECMAKE += "${@ '-DWEBOS_DISTRO_PRERELEASE:STRING="${WEBOS_DISTRO_PRERELEASE}"' \
                  if d.getVar('WEBOS_DISTRO_PRERELEASE') != '' else ''}"

# Currently always using the modules for the rockhopper core OS.
WEBOS_TARGET_CORE_OS = "rockhopper"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_machine_dep
inherit webos_machine_impl_dep
inherit webos_prerelease_dep
inherit webos_core_os_dep
inherit webos_nyx_module_provider
inherit webos_distro_variant_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
