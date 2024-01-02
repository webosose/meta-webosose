# Copyright (c) 2012-2024 LG Electronics, Inc.

SUMMARY = "webOS of the open-source FreeBSD memory allocation library"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "libs"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = " \
    file://README.md;beginline=94;md5=b159f6c7121460dba2c5965602bc3268 \
    file://oss-pkg-info.yaml;md5=a062b932da63efa0c8ac4060de1864cf \
"

WEBOS_VERSION = "0.20080828a-0webos9-6_f527e94e1facd3a9c801bf181d5e7cba287d04bb"
PR = "r5"

inherit webos_upstream_from_repo
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
