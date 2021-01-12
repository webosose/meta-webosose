# Copyright (c) 2015-2021 LG Electronics, Inc.

SUMMARY = "Event Monitoring Service for generic notifications"
AUTHOR = "Viesturs Zarins <viesturs.zarins@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 luna-service2 pmloglib libpbnjson libwebosi18n"

WEBOS_VERSION = "1.1.0-8_ad9c9942eec3855f7d82b863f8be2b579d8edc7e"
PR = "r3"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_event_monitor_plugin
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

#Enable/disable mock plugin
EXTRA_OECMAKE += "-DBUILD_MOCK_PLUGIN:BOOL='NO'"

#webos_event_monitor_plugin depends on event-monitor, remove circular dependency
WEBOS_EVENT_MONITOR_PLUGIN_DEPENDS = ""
