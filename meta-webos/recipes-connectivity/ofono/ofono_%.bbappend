# Copyright (c) 2020 LG Electronics, Inc.

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO_append = "webos1"

SRC_URI += " \
    file://0001-Fix-ougoing-call-hangup-not-responding.patch \
"
