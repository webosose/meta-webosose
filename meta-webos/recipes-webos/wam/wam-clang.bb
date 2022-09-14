# Copyright (c) 2023 LG Electronics, Inc.

require wam.bb

inherit clang_cmake

BPN = "wam"

PROVIDES = "virtual/webappmanager-webos"

WEBOS_REPO_NAME = "wam"

PR = "r0"

FILESEXTRAPATHS:prepend := "${THISDIR}/wam:"

DEPENDS += "chromium-toolchain-native chromium-stdlib"
DEPENDS:remove = "jsoncpp"
DEPENDS += "jsoncpp-clang"

OECMAKE_CXX_FLAGS += "\
    -Wno-error=unused-command-line-argument \
    -Wno-error=inconsistent-missing-override \
    -Wno-format \
"
