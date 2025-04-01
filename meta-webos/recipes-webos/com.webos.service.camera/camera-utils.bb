# Copyright (c) 2024-2025 LG Electronics, Inc.

SUMMARY = "Utility library for camera service"
AUTHOR = "Sungho Lee <shl.lee@lge.com>"
SECTION = "webos/libs"

require com.webos.service.camera.inc

WEBOS_REPO_NAME = "com.webos.service.camera"

PR = "${INC_PR}.0"

DEPENDS = "glib-2.0 luna-service2 pmloglib nlohmann-json"

EXTRA_OECMAKE += "-DBUILD_UTILS_ONLY=ON"
