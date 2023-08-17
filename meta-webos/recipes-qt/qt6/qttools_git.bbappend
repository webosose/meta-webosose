# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# backport the fix from:
# https://codereview.qt-project.org/c/yocto/meta-qt6/+/476890 qttools: add dependency on llvm-native
DEPENDS += "llvm-native"
