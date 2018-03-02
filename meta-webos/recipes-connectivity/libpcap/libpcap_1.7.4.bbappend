# Copyright (c) 2014-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# Only enable bluetooth support when we're using bluez4 as otherwise we
# don't provide support (override default PACKAGECONFIG setting in main recipe)
PACKAGECONFIG_BLUETOOTH = "${@ 'bluetooth' if '${VIRTUAL-RUNTIME_bluetooth_stack}' == 'bluez4' else '' }"
PACKAGECONFIG = "${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', '${PACKAGECONFIG_BLUETOOTH}', '', d)}"
