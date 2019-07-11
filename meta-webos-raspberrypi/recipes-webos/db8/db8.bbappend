# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi1"

FILESEXTRAPATHS_prepend_rpi := "${THISDIR}/${BPN}:"

SRC_URI_append_rpi = " file://0001-apollo-empty-launcher-no-space-db8-fix.patch"
