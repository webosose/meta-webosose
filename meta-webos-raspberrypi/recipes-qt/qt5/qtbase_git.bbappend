# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi4"

FILESEXTRAPATHS_prepend_rpi := "${THISDIR}/${BPN}:"
SRC_URI_append_rpi = " \
    file://0001-Disable-eglfs-brcm-explicitly.patch \
    file://0002-Update-eglfs_kms-to-render-on-topmost-drmplane-Relea.patch \
    file://0003-Fix-avoutputd-lsm-start-sequence-issue.patch \
"

PACKAGECONFIG_append_rpi = " eglfs evdev kms udev"

EXTRA_QMAKEVARS_PRE_append_rpi = " INCLUDEPATH+=${STAGING_INCDIR}/drm"
