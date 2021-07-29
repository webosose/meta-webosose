# Copyright (c) 2014-2019 LG Electronics, Inc.

# You don't need to change this value when you're changing just the RDEPENDS:${PN} variable.
EXTENDPRAUTO:append = "webos3"

# Don't add the packages related to LTTng if it's not enabled
WEBOS_LTTNG_ENABLED ??= "0"
DISTROOVERRIDES .= "${@ '' if '${WEBOS_LTTNG_ENABLED}' == '1' else ':no-lttng' }"

LTTNGUST:no-lttng = ""
LTTNGTOOLS:no-lttng= ""
LTTNGMODULES:no-lttng = ""
BABELTRACE:no-lttng = ""
