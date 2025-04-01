# Copyright (c) 2013-2025 LG Electronics, Inc.
#
# webos_prebuilt_binaries
#
# If what's fetched by the SRC_URI for a component contains one or more prebuilt
# binaries (perhaps in addition to source), then its recipe must inherit from
# this bbclass.


# Set WEBOS_PREBUILT_BINARIES_FOR to the space separated list of MACHINEOVERRIDES
# values for which a given SRC_URI value (including the checksums) can be used.
# It is processed into a value for COMPATIBLE_MACHINE.
#
# For ARM binaries, the value typically used is the list of ARMPKGARCH values
# (armv<N>|aarch64) corresponding to the architectures compatible with the -march
# argument used when the prebuilt binaries fetched by SRC_URI were compiled.
#
# If a different SRC_URI is fetched for every MACHINE, set
# WEBOS_PREBUILT_BINARIES_FOR to each value of it in separate recipes that all
# inherit from webos_machine_dep. If MACHINE-s belonging to the same SOC_FAMILY
# can use what's fetched from the same SRC_URI, set WEBOS_PREBUILT_BINARIES_FOR
# to the value of SOC_FAMILY in a separate recipe that inherits from
# webos_soc_family_dep.
#
# If a different SRC_URI is fetched for every TUNE_PKGARCH, set
# WEBOS_PREBUILT_BINARIES_FOR each value of it in separate recipes that do not
# inherit from webos_machine_dep or webos_soc_family_dep.
#
# If the SRC_URI fetched is usable on all ARM architecture MACHINE-s for which
# the distro is being built, use the value "arm".
#
# Note that since the MACHINEOVERRIDES values for little-endian and big-endian
# are the same, if a distro is being built for MACHINE-s with a mixture of
# endianness, binaries for a value specified in WEBOS_PREBUILT_BINARIES_FOR list
# must be available for both endian flavors.
#
# For Intel binaries, use "x86" if the prebuilt binaries were compiled with
# -m32, "x86_64" if they were compiled with -m64, and "x86_64_x32" if they were
# compiled with -mx32. (Note that the "x86_64*" values are not added to
# MACHINEOVERRIDES by the architecture tuning includes provided by oe-core; they
# must added explicitly by something in the BSP layer.
#
# There are also two special values: "all" means the binaries that are fetched by
# SRC_URI are usable on all of the MACHINE-s for which the distro is being built
# and "none" means that what's fetched is not usable on any of the MACHINE-s. The
# latter value is intended to be used when WEBOS_PREBUILT_BINARIES_FOR will be
# overridden. If these are used, no other values can appear in
# WEBOS_PREBUILT_BINARIES_FOR.

WEBOS_PREBUILT_BINARIES_FOR ??= "none"

# WEBOS_PREBUILT_BINARIES_FOR_ARCH can be set for additional restrictions, because
# the MACHINE specific WEBOS_PREBUILT_BINARIES_FOR values still do exist in 2 variants
# e.g. for arm in lib32- multilib builds and aarch64 for regular 64bit builds, which
# need to point to diferent tarball (hence the prepend to WEBOS_TCMONIKER) and needs
# COMPATIBLE_HOST restrictions to make sure right recipe is pulled into right build.
#
# Typical values match COMPATIBLE_HOST prefix (except the x86 case),
# e.g. "all", "x86_64", "x86", "aarch64", "arm".
WEBOS_PREBUILT_BINARIES_FOR_ARCH ??= ""

# WEBOS_TCMONIKER is distinguished by WEBOS_PREBUILT_BINARIES_FOR_ARCH
# In all or empty(32-bit) case, we don't prepend WEBOS_TCMONIKER according to
# binary naming rule.
WEBOS_TCMONIKER_ARCH_PREPEND = "${@bb.utils.contains_any('WEBOS_PREBUILT_BINARIES_FOR_ARCH', 'all arm x86', '', '${WEBOS_PREBUILT_BINARIES_FOR_ARCH}', d)}"
WEBOS_TCMONIKER:prepend = "${WEBOS_TCMONIKER_ARCH_PREPEND}"

# If not MACHINE-dependent, the binaries can be TUNE_PKGARCH-dependent. It's OK
# to have TUNE_PKGARCH be the choice before MACHINE in MACHINEOVERRIDES: the test
# for inheriting webos_machine_dep or 'webos_soc_family_dep is false, so we know
# MACHINE and SOC_FAMILY aren't being used.
MACHINEOVERRIDES .= "${@ \
    '' \
    if bb.data.inherits_class('webos_machine_dep', d) or bb.data.inherits_class('webos_soc_family_dep', d) else \
    ':${TUNE_PKGARCH}' \
}"

# Allow other assignments to COMPATIBLE_HOST done by the recipe to have
# precedence.
COMPATIBLE_HOST ??= "${@ \
    dict(all='^.*$', \
         x86_64='x86_64.*-linux', \
         x86='i.86.*-linux', \
         aarch64='aarch64.*-linux', \
         arm='arm.*-linux').get('${WEBOS_PREBUILT_BINARIES_FOR_ARCH}', '^$')}"

# Allow other assignments to COMPATIBLE_MACHINE done by the recipe to have
# precedence.
COMPATIBLE_MACHINE ??= "${@ \
    dict(all='^.*$', \
         none='^$', \
         x86='^(i.86|x86_64)$', \
         arm='^(armv.*|aarch64)$').get('${WEBOS_PREBUILT_BINARIES_FOR}', \
                                       '^(' + '|'.join('${WEBOS_PREBUILT_BINARIES_FOR}'.split()) + ')$') }"
