# Copyright (c) 2020 LG Electronics, Inc.

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos2"

SRC_URI += " \
    file://0001-Fix-ougoing-call-hangup-not-responding.patch \
    file://0001-Fix-pulseaudio-hfp-device-error.patch \
"
