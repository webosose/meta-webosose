# Copyright (c) 2024-2025 LG Electronics, Inc.

SUMMARY = "Utility library for camera service"
AUTHOR = "Sungho Lee <shl.lee@lge.com>"
SECTION = "webos/libs"

require com.webos.service.camera.inc

WEBOS_REPO_NAME = "com.webos.service.camera"

S = "${WORKDIR}/git/src/common"

PR = "${INC_PR}.1"

DEPENDS = "glib-2.0 luna-service2 pmloglib nlohmann-json"

# FIXME-buildpaths!!!
# File /usr/lib/.debug/libcamera_common.so.1.0.0 in package camera-utils-dbg contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
