# Copyright (c) 2020 LG Electronics, Inc.

inherit webos_qmake5_base

EXTENDPRAUTO_append = "webos3"

# ${BPN} does not get "qtbase" for some reason
FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

# Upstream-Status: Inappropriate
SRC_URI_append = " \
    file://9901-Add-webos-oe-g-and-webos-oe-clang-platforms.patch \
"
