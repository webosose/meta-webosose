# Copyright (c) 2020-2025 LG Electronics, Inc.

DESCRIPTION = "Utility library used in Location Framework"
AUTHOR = "Muralidhar N <muralidhar.n@lge.com>"
SECTION = "webos/location"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=2763f3ed850f8412903ea776e0526bea \
    file://oss-pkg-info.yaml;md5=71729d222d4be4c1e2518bb8770abeee \
"

DEPENDS = "glib-2.0 curl pmloglib"

WEBOS_VERSION = "1.0.0-23_5649c5d78db08e14a7be014c80a5dc4e169c56ae"
PR = "r5"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
