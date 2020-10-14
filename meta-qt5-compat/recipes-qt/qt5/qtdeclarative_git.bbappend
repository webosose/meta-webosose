# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "qt5-compat1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append = " \
    file://0026-Use-python3-explicitly.patch \
"
