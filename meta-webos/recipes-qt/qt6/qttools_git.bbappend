# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

# backport the fix from:
# https://codereview.qt-project.org/c/yocto/meta-qt6/+/482948 qttools: add PACKAGECONFIG for clang dependency
PACKAGECONFIG[clang] = "-DFEATURE_clang=ON,-DFEATURE_clang=OFF,clang"
