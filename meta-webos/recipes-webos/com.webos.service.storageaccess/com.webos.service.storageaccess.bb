# Copyright (c) 2021-2022 LG Electronics, Inc.

SUMMARY = "Storage Access Framework for OSE"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
    file://oss-pkg-info.yaml;md5=13b5f44cefd7b1b0040a056eeddf6174 \
"

DEPENDS= "glib-2.0 libxml2 luna-service2 pmloglib libgdrive libpbnjson curl gupnp"

WEBOS_VERSION = "1.0.0-22_7ff54f4abf4ec7bbc25edda082813694c534e44b"
PR = "r6"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_system_bus
inherit webos_machine_dep
inherit webos_distro_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
