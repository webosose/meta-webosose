# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi9"

FILESEXTRAPATHS_prepend_rpi := "${THISDIR}/${BPN}:"

# Patches from 5.12.meta-webos-raspberrypi.5 based on 5.12.meta-webos.5
SRC_URI_append_rpi = " \
    file://0001-Disable-eglfs-brcm-explicitly.patch \
    file://0002-Update-eglfs_kms-to-render-on-topmost-drmplane.patch \
    file://0003-Fix-avoutputd-videooutputd-lsm-start-sequence-issue.patch \
    file://0004-Update-eglfs-to-fix-composition-of-graphic-and-video.patch \
"

PACKAGECONFIG_append_rpi = " eglfs evdev gbm kms udev"

EXTRA_QMAKEVARS_PRE_append_rpi = " INCLUDEPATH+=${STAGING_INCDIR}/drm"
