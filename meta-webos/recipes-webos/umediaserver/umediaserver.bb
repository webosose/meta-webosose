# Copyright (c) 2013-2024 LG Electronics, Inc.

require umediaserver.inc

PR = "${INC_PR}.0"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "umediaserver.service"
