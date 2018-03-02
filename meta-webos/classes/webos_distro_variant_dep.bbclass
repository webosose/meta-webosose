# Copyright (c) 2014-2018 LG Electronics, Inc.
#
# webos_distro_variant_dep
#
# This class is to be inherited by the recipe for every component that depends
# on WEBOS_TARGET_DISTRO_VARIANT or WEBOS_DISTRO_NAME_SUFFIX at build time.
# When different from the default ("normal"), WEBOS_TARGET_DISTRO_VARIANT is
# set in MACHINE.conf and WEBOS_DISTRO_NAME_SUFFIX is set in
# distro/include/<DISTRO>-<WEBOS_TARGET_DISTRO_VARIANT>.inc .
#
# Inheriting this class arranges for two overrides to be added that are selected
# ahead of "<DISTRO>":
# - "distrovariant-<WEBOS_TARGET_DISTRO_VARIANT>", which is meant to be used when
#   an override applies to all DISTRO-s that have a particular variant.
# - "<DISTRO>-<WEBOS_TARGET_DISTRO_VARIANT>", which is meant to be used when an
#   override applies to a particular DISTRO with that variant.
#
# "distrovariant-<WEBOS_TARGET_DISTRO_VARIANT>" is selected ahead of
# "<DISTRO>-<WEBOS_TARGET_DISTRO_VARIANT>".


# Append this to EXTRA_OEMAKE to allow your makefile be distro variant-dependent:
WEBOS_EXTRA_OEMAKE_DISTRO_VARIANT_DEP = "WEBOS_TARGET_DISTRO_VARIANT=${WEBOS_TARGET_DISTRO_VARIANT}"
