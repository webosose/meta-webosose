# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

SRC_URI_append = "file://0001-Fix-return-type-error.patch"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
