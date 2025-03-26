# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895263
# ERROR: QA Issue: File /usr/bin/cam in package libcamera contains reference to TMPDIR
# File /usr/libexec/libcamera/raspberrypi_ipa_proxy in package libcamera contains reference to TMPDIR
# File /usr/libexec/libcamera/v4l2-compat.so in package libcamera contains reference to TMPDIR
# File /usr/lib/libcamera.so.0.3.0 in package libcamera contains reference to TMPDIR
# File /usr/lib/libcamera-base.so.0.3.0 in package libcamera contains reference to TMPDIR
# File /usr/lib/libcamera/ipa_rpi_vc4.so in package libcamera contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
