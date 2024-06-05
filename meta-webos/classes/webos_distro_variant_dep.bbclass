# Copyright (c) 2014-2024 LG Electronics, Inc.
#
# webos_distro_variant_dep
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
