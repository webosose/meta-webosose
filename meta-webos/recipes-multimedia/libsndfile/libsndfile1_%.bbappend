# Copyright (c) 2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://CVE-2017-14245.patch"

