# Copyright (c) 2017-2020 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi11"

FILESEXTRAPATHS_prepend_rpi := "${THISDIR}/${BPN}:"

# Patches from 5.12.meta-webos-raspberrypi.10 based on 5.12.meta-webos.17 (specific to raspberrypi3)
SRC_URI_append_raspberrypi3 = " \
    file://0001-Update-eglfs_kms-to-render-on-topmost-drmplane.patch \
    file://0002-Fix-avoutputd-videooutputd-lsm-start-sequence-issue.patch \
    file://0003-Update-eglfs-to-fix-composition-of-graphic-and-video.patch \
"

# Patches from 5.12.meta-webos-raspberrypi.11 based on 5.12.meta-webos.17 (specific to raspberrypi4)
# The patch below addresses an issue where the cursor on the first display is duplicated
# on the second at a very large magnification. Unlike other Raspberry Pi devices,
# Raspberry Pi 4 must use FKMS. This may be the cause of the problem.
# TODO: This patch will remove the artifact but another issue still exists where
# the cursor is suddenly appeared when moving it between displays.
SRC_URI_append_raspberrypi4 = " \
    file://0001-Fix-the-cursor-problem-on-secondary-display-on-Raspb.patch \
"

PACKAGECONFIG_append_rpi = " eglfs evdev gbm kms udev"

EXTRA_QMAKEVARS_PRE_append_rpi = " INCLUDEPATH+=${STAGING_INCDIR}/drm"
