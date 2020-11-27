# Copyright (c) 2021 LG Electronics, Inc.

LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=916569994c13b72c74223963ee65895d"

WEBOS_REPO_NAME = "chromium84"
WEBOS_VERSION = "84.0.4147.89-7_ebfefdb1fab0b601ab6a4b41f2ac6fa92f17301b"

WEBOS_REPO_NAME_V8 = "chromium-v8"
WEBOS_GIT_PARAM_BRANCH_V8 = "@chromium84"
WEBOS_VERSION_V8 = "8.4.371.19-chromium84.2_9ea4957fd61303416e2dc36235c5776240472d44"
SRCREV_v8 = "5c1d89dd2945a10cf7a6a3458050b3177a870b09"

# Fix build error for aarch64
# http://gpro.lge.com/c/webosauto/chromium84/+/285079
SRC_URI_append_aarch64 = "\
    file://0001-op-ds-build-Fix-build-error-when-target-is-arm64.patch \
"
