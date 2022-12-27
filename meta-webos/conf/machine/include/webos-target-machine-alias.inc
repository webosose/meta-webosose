# Copyright (c) 2015-2023 LG Electronics, Inc.
#
# webos-target-machine-alias.inc
#
# This file is to be require-d by all MACHINE.conf-s that specify a machine
# alias. It is expected that it will set WEBOS_TARGET_MACHINE_ALIAS before
# the require.
#

MACHINEOVERRIDES .= "${@ \
    '' \
    if bb.data.inherits_class('webos_machine_actual_dep', d) else \
    ':${WEBOS_TARGET_MACHINE_ALIAS}' \
}"

WEBOS_TARGET_MACHINE_ACTUAL = "${@ \
    '${MACHINE}' \
    if bb.data.inherits_class('webos_machine_actual_dep', d) else \
    '${WEBOS_TARGET_MACHINE_ALIAS}' \
}"
WEBOS_TARGET_MACHINE_ACTUAL[vardepvalue] = "${WEBOS_TARGET_MACHINE_ACTUAL}"

WEBOS_MACHINE = "${WEBOS_TARGET_MACHINE_ACTUAL}"
WEBOS_SIGNTOOL_MACHINE = "${WEBOS_TARGET_MACHINE_ACTUAL}"
PARTINFO_MACHINE = "${WEBOS_TARGET_MACHINE_ACTUAL}"
DEVICE_TABLE_MACHINE = "${WEBOS_TARGET_MACHINE_ACTUAL}"
WEBOS_QMAKE_MACHINE_ACTUAL = "${WEBOS_TARGET_MACHINE_ACTUAL}"
EXTRA_OECMAKE_MACHINE_ACTUAL = "${WEBOS_TARGET_MACHINE_ACTUAL}"
