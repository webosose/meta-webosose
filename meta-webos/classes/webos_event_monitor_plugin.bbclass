# Copyright (c) 2015-2023 LG Electronics, Inc.
#
#
# This class is to be inherited by the recipe for every component that installs
# a event monitor plugin
#

WEBOS_EVENT_MONITOR_PLUGIN_DEPENDS = "event-monitor"
DEPENDS:append = " ${WEBOS_EVENT_MONITOR_PLUGIN_DEPENDS}"

WEBOS_EVENT_MONITOR_PLUGIN_PATH = "${libdir}/eventmonitor"
EXTRA_OECMAKE += "-DWEBOS_EVENT_MONITOR_PLUGIN_PATH:STRING='${WEBOS_EVENT_MONITOR_PLUGIN_PATH}'"

FILES:${PN} += "${WEBOS_EVENT_MONITOR_PLUGIN_PATH}"
