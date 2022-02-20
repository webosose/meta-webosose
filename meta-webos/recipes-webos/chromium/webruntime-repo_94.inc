# Copyright (c) 2022 LG Electronics, Inc.

LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=dac1866e82f07906c382718584e415e5"

WEBOS_REPO_NAME = "chromium94"

WEBOS_REPO_NAME_V8 = "chromium-v8"
WEBOS_GIT_PARAM_BRANCH_V8 = "@chromium94"
WEBOS_VERSION_V8 = "9.1.269.36-chromium94.1_41a8a1fdbb4d78a52855d378e3d6600f29015fc8"
SRCREV_v8 = "36c5abcbfb0919dc2be3ffebb3b0cc146ac8a45e"

SRC_URI += " \
    file://0003-jinja2-fix-compatibility-with-python3-3.10.patch \
"
