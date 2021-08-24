# Copyright (c) 2021 LG Electronics, Inc.

LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=28f5b870e5e06b9af5b96c9dd65b31c8"

WEBOS_REPO_NAME = "chromium87"
WEBOS_VERSION = "87.0.4280.88-7_76fa38e6b103b248c21fa6fbaf40fd893293755e"

WEBOS_REPO_NAME_V8 = "chromium-v8"
WEBOS_GIT_PARAM_BRANCH_V8 = "@chromium87"
WEBOS_VERSION_V8 = "8.7.220.29-chromium87.4_e8a7ab899d76b1e8f48457dbb57178d6bd3a4d02"
SRCREV_v8 = "935065d097cce0090bc858746e6aee0919b222de"

# TODO: Apply all patches from
SRC_URI += " \
    file://0001-absei-cpp-Fix-build-with-gcc-11.patch \
    file://0002-perfetto-Fix-build-with-gcc-11.patch \
    file://0003-net-Fix-build-with-gcc-11.patch \
"