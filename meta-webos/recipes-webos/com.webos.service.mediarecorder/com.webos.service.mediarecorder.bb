# Copyright (c) 2023-2025 LG Electronics, Inc.

require com.webos.service.mediarecorder.inc

PR = "${INC_PR}.0"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "com.webos.service.mediarecorder.service"

PACKAGECONFIG += "build-media-recorder test-apps"

DEPENDS:append = " umediaserver media-resource-calculator"
