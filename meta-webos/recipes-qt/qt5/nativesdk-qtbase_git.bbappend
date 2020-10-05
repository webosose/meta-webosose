# Copyright (c) 2020 LG Electronics, Inc.

inherit webos_qmake5_base

EXTENDPRAUTO_append = "webos2"

# ${BPN} does not get "qtbase" for some reason
FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

SRC_URI_append = " \
    file://0001-Add-webos-oe-g-and-webos-oe-clang-platforms.patch \
"
