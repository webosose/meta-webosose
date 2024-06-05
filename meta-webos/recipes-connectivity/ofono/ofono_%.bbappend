# Copyright (c) 2020-2024 LG Electronics, Inc.

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos3"

SRC_URI += " \
    file://0001-Fix-ougoing-call-hangup-not-responding.patch \
    file://0001-Fix-pulseaudio-hfp-device-error.patch \
"

# http://gecko.lge.com:8000/Errors/Details/821863
# ofono-2.4/drivers/hfpmodem/voicecall.c:1081:17: error: implicit declaration of function 'usleep'; did you mean 'g_usleep'? [-Wimplicit-function-declaration]
CFLAGS += "-Wno-error=implicit-function-declaration"
