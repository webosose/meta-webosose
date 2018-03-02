# Copyright (c) 2015 LG Electronics, Inc.
#
# webos_distro_dep
#
# This class is to be inherited by the recipe for every component that depends
# on DISTRO at build time.
#

# Append this to EXTRA_OEMAKE to allow your makefile be DISTRO-dependent:
WEBOS_EXTRA_OEMAKE_DISTRO_DEP = "DISTRO=${DISTRO}"
