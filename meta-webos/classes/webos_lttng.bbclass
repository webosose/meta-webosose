# Copyright (c) 2013-2025 LG Electronics, Inc.
#
# webos_lttng
#
# This class is to be inherited by the recipe for any component that
# uses LTTng tracing.
#
# Each recipe is responsible for setting a compilation flag to enable
# its own LTTng tracepoints based on the value of WEBOS_LTTNG_ENABLED.

# LTTng is disabled by default for production builds. To enable, add:
#    WEBOS_LTTNG_ENABLED = "1"
# to your webos-local.conf or the location of your choice.

inherit webos_prerelease_dep
WEBOS_LTTNG_ENABLED ??= "0"
WEBOS_LTTNG_ENABLED ?= "${@ '0' if '${WEBOS_DISTRO_PRERELEASE}' == '' else '1' }"
# Only enable LTTng for target components
WEBOS_LTTNG_ENABLED:class-native = "0"
WEBOS_LTTNG_ENABLED:class-nativesdk = "0"

# Use _append so that WEBOS_LTTNG_ENABLED is evaluated during finalization so that the overrides effectual.
DEPENDS:append = "${@ ' lttng-ust' if '${WEBOS_LTTNG_ENABLED}' == '1' else ''}"
RDEPENDS:${PN}:append = "${@ ' lttng-tools lttng-modules babeltrace' if '${WEBOS_LTTNG_ENABLED}' == '1' else ''}"
