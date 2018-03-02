# Copyright (c) 2012-2013 LG Electronics, Inc.
#
# webos_machine_impl_dep
#
# This class is to be inherited by the recipe for every component that depends
# on the "machine implementation" at build time.

# If there are MACHINE-s with different "machine implementations" but with the
# same TUNE_PKGARCH, they will have the same package name unless PACKAGE_ARCH
# is set to MACHINE_ARCH. XXX Is there a better way to handle this?
PACKAGE_ARCH = "${MACHINE_ARCH}"

# Allow the use of WEBOS_TARGET_MACHINE_IMPL overrides. It's checked after
# what's set by the machine architecture .inc file, which is checked after
# MACHINE. WEBOS_EXTRA_MACHINEOVERRIDES is deliberately assigned to and not
# appended to or prepended to so that the order of inherit statements will not
# affect the order of what's in MACHINEOVERRIDES.

WEBOS_EXTRA_MACHINEOVERRIDES = "${WEBOS_TARGET_MACHINE_IMPL}:"
