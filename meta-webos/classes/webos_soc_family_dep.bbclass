# Copyright (c) 2014 LG Electronics, Inc.
#
# webos_soc_family_dep
#
# This class is to be inherited by the recipe for every component that depends
# on SOC_FAMILY at build time. When used, SOC_FAMILY is set in MACHINE.conf,
#

SOC_FAMILY ??= ""

# XXX Perhaps someday we'll want to set PACKAGE_ARCH to SOC_FAMILY.
PACKAGE_ARCH = "${MACHINE_ARCH}"

# Append this to EXTRA_OEMAKE to allow your makefile be SOC_FAMILY-dependent:
WEBOS_EXTRA_OEMAKE_SOC_FAMILY_DEP = "SOC_FAMILY=${SOC_FAMILY}"
