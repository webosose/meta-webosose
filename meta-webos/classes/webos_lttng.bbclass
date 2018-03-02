# Copyright (c) 2013 LG Electronics, Inc.
#
# webos_lttng
#
# This class is to be inherited by the recipe for any component that
# uses LTTng tracing.
#
# Each recipe is responsible for setting a compilation flag to enable
# its own LTTng tracepoints based on the value of WEBOS_LTTNG_ENABLED.

# LTTng is disabled by default. To enable, add:
#    WEBOS_LTTNG_ENABLED = "1"
# to your webos-local.conf or the location of your choice.
WEBOS_LTTNG_ENABLED ??= "0"
# Only enable LTTng for target components
WEBOS_LTTNG_ENABLED_class-native = "0"
WEBOS_LTTNG_ENABLED_class-nativesdk = "0"

# Use _append so that WEBOS_LTTNG_ENABLED is evaluated during finalization so that the overrides effectual.
DEPENDS_append = "${@ ' lttng-ust' if '${WEBOS_LTTNG_ENABLED}' == '1' else ''}"
RDEPENDS_${PN}_append = "${@ ' lttng-tools lttng-modules babeltrace' if '${WEBOS_LTTNG_ENABLED}' == '1' else ''}"
