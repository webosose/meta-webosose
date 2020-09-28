# Copyright (c) 2014-2020 LG Electronics, Inc.

# You don't need to change this value when you're changing just the RDEPENDS_${PN} variable.
EXTENDPRAUTO_append = "webos4"

# Don't add the packages related to LTTng if it's not enabled
WEBOS_LTTNG_ENABLED ??= "0"
DISTROOVERRIDES .= "${@ '' if '${WEBOS_LTTNG_ENABLED}' == '1' else ':no-lttng' }"

LTTNGUST_no-lttng = ""
LTTNGTOOLS_no-lttng= ""
LTTNGMODULES_no-lttng = ""
BABELTRACE_no-lttng = ""

# We're using old elfutils version which isn't compatible with systemtap
SYSTEMTAP_remove = "systemtap"
