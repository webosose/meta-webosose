# Copyright (c) 2021 LG Electronics, Inc.

LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=916569994c13b72c74223963ee65895d"

WEBOS_REPO_NAME = "chromium84"
WEBOS_VERSION = "84.0.4147.89-12_e3976633b09aae666a3f28e326d73afac205d0bb"

WEBOS_REPO_NAME_V8 = "chromium-v8"
WEBOS_GIT_PARAM_BRANCH_V8 = "@chromium84"
WEBOS_VERSION_V8 = "8.4.371.19-chromium84.3_d54017133984b1216f93ffae9da309db4fa9b758"
SRCREV_v8 = "b8071b2f0c803b36e56226b5c21ac180ed793ee9"

# Fix build error for aarch64
# http://gpro.lge.com/c/webosauto/chromium84/+/285079
SRC_URI_append_aarch64 = "\
    file://0001-op-ds-build-Fix-build-error-when-target-is-arm64.patch \
"
