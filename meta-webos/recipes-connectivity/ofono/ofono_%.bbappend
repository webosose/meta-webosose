# Copyright (c) 2020 LG Electronics, Inc.

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos1"

SRC_URI += " \
    file://0001-Fix-ougoing-call-hangup-not-responding.patch \
"
