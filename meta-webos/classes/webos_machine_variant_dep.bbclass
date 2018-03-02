# Copyright (c) 2015-2017 LG Electronics, Inc.
#
# webos_machine_variant_dep
#
# This class is to be inherited by the recipe for every component that depends
# on WEBOS_TARGET_MACHINE_VARIANT.
# When different from the default ("none"), WEBOS_TARGET_MACHINE_VARIANT is
# set in MACHINE.conf
#

# The default value of WEBOS_TARGET_MACHINE_VARIANT is "none" and MACHINE which defines
# the value of it is the secondary MACHINE which has WEBOS_TARGET_MACHINE_ALIAS.
inherit webos_machine_dep

WEBOS_EXTRA_MACHINEOVERRIDES_VARIANT = "${@ \
    '${WEBOS_TARGET_MACHINE_VARIANT}:' \
    if d.getVar('WEBOS_TARGET_MACHINE_VARIANT', True) else \
    '' \
}"

# Append this to EXTRA_OEMAKE to allow your makefile be distro variant-dependent:
WEBOS_EXTRA_OEMAKE_MACHINE_VARIANT_DEP = "${@ \
    'WEBOS_TARGET_MACHINE_VARIANT=${WEBOS_TARGET_MACHINE_VARIANT}' \
    if not bb.data.inherits_class('native', d) else \
    '' \
}"
WEBOS_EXTRA_OEMAKE_MACHINE_VARIANT_DEP[vardepvalue] = "${WEBOS_EXTRA_OEMAKE_MACHINE_VARIANT_DEP}"
