# Copyright (c) 2012-2023 LG Electronics, Inc.
#
# webos_cmake
#
# This class is to be inherited by every recipe in meta-webos whose component
# uses CMake. It adds a dependency on cmake-modules-webos-native, which will be
# extraneous until the component is converted, but who cares?
#
# Expects that webos_submissions or webos_enhanced_submissions will also be
# inherited (for WEBOS_COMPONENT_VERSION).

# Extra variable is needed to be able to inhibit this dependency in case
# we have some recipe which can reuse this bbclass but without this dependency.
WEBOS_CMAKE_DEPENDS = "cmake-modules-webos-native"
DEPENDS:append = " ${WEBOS_CMAKE_DEPENDS}"

inherit cmake
inherit webos_filesystem_paths

WEBOS_PKGCONFIG_BUILDDIR = "${B}"

EXTRA_OECMAKE += "-DWEBOS_INSTALL_ROOT:PATH=/"

WEBOS_TARGET_DISTRO_VARIANT ??= "bitbake-conf-in-meta-webos-was-not-parsed"
WEBOS_TARGET_MACHINE_IMPL ??= "invalid-missing-inherit-webos_machine_impl_dep"
WEBOS_TARGET_MACHINE_VARIANT ??= "invalid-missing-inherit-webos_machine_variant_dep"
WEBOS_TARGET_CORE_OS ?= "rockhopper"

# XXX Should error if WEBOS_COMPONENT_VERSION is unset
EXTRA_OECMAKE += "-DWEBOS_COMPONENT_VERSION:STRING=${WEBOS_COMPONENT_VERSION}"

EXTRA_OECMAKE_TARGET_CORE_OS = "${@ \
    '-DWEBOS_TARGET_CORE_OS:STRING=${WEBOS_TARGET_CORE_OS}' \
    if bb.data.inherits_class('webos_core_os_dep', d) and not bb.data.inherits_class('native', d) else \
    '' \
}"
EXTRA_OECMAKE_TARGET_CORE_OS[vardepvalue] = "${EXTRA_OECMAKE_TARGET_CORE_OS}"
EXTRA_OECMAKE += "${EXTRA_OECMAKE_TARGET_CORE_OS}"

# XXX Add webos_kernel_dep() to webOS.cmake that adds WEBOS_TARGET_KERNEL_HEADERS to the search path
EXTRA_OECMAKE_KERNEL_HEADERS = "${@ \
    '-DWEBOS_TARGET_KERNEL_HEADERS:STRING=${STAGING_KERNEL_DIR}/include' \
    if bb.data.inherits_class('webos_kernel_dep', d) and not bb.data.inherits_class('native', d) else \
    '' \
}"
EXTRA_OECMAKE_KERNEL_HEADERS[vardepsexclude] = "${@ \
    '' \
    if bb.data.inherits_class('webos_kernel_dep', d) and not bb.data.inherits_class('native', d) else \
    'STAGING_KERNEL_DIR' \
}"
EXTRA_OECMAKE += "${EXTRA_OECMAKE_KERNEL_HEADERS}"

EXTRA_OECMAKE_MACHINE_ACTUAL ?= "${MACHINE}"
EXTRA_OECMAKE_MACHINE = "${@ \
    '-DWEBOS_TARGET_MACHINE:STRING=${EXTRA_OECMAKE_MACHINE_ACTUAL}' \
    if bb.data.inherits_class('webos_machine_dep', d) and not bb.data.inherits_class('native', d) else \
    '' \
}"
EXTRA_OECMAKE_MACHINE[vardepvalue] = "${EXTRA_OECMAKE_MACHINE}"
EXTRA_OECMAKE += "${EXTRA_OECMAKE_MACHINE}"

# If SOC_FAMILY is empty, don't add -DWEBOS_TARGET_SOC_FAMILY.
EXTRA_OECMAKE_SOC_FAMILY = "${@ \
    '-DWEBOS_TARGET_SOC_FAMILY:STRING=' + d.getVar('SOC_FAMILY', True) \
    if bb.data.inherits_class('webos_soc_family_dep', d) and not bb.data.inherits_class('native', d) and (d.getVar('SOC_FAMILY', True) or '') != '' else \
    '' \
}"
EXTRA_OECMAKE_SOC_FAMILY[vardepvalue] = "${EXTRA_OECMAKE_SOC_FAMILY}"
EXTRA_OECMAKE += "${EXTRA_OECMAKE_SOC_FAMILY}"

EXTRA_OECMAKE_MACHINE_IMPL = "${@ \
    '-DWEBOS_TARGET_MACHINE_IMPL:STRING=${WEBOS_TARGET_MACHINE_IMPL}' \
    if bb.data.inherits_class('webos_machine_impl_dep', d) and not bb.data.inherits_class('native', d) else \
    '' \
}"
EXTRA_OECMAKE_MACHINE_IMPL[vardepvalue] = "${EXTRA_OECMAKE_MACHINE_IMPL}"
EXTRA_OECMAKE += "${EXTRA_OECMAKE_MACHINE_IMPL}"

EXTRA_OECMAKE_MACHINE_VARIANT = "${@ \
    '-DWEBOS_TARGET_MACHINE_VARIANT:STRING=${WEBOS_TARGET_MACHINE_VARIANT}' \
    if bb.data.inherits_class('webos_machine_variant_dep', d) and not bb.data.inherits_class('native', d) else \
    '' \
}"
EXTRA_OECMAKE_MACHINE_VARIANT[vardepvalue] = "${EXTRA_OECMAKE_MACHINE_VARIANT}"
EXTRA_OECMAKE += "${EXTRA_OECMAKE_MACHINE_VARIANT}"

# If DISTRO is unset, don't add -DWEBOS_TARGET_DISTRO. If it is set, always pass
# it, even for -native components.
EXTRA_OECMAKE_DISTRO = "${@ \
    '-DWEBOS_TARGET_DISTRO:STRING=' + d.getVar('DISTRO', True) \
    if bb.data.inherits_class('webos_distro_dep', d) and (d.getVar('DISTRO', True) or '') != '' else \
    '' \
}"
EXTRA_OECMAKE_DISTRO[vardepvalue] = "${EXTRA_OECMAKE_DISTRO}"
EXTRA_OECMAKE += "${EXTRA_OECMAKE_DISTRO}"

EXTRA_OECMAKE_DISTRO_VARIANT = "${@ \
    '-DWEBOS_TARGET_DISTRO_VARIANT:STRING=${WEBOS_TARGET_DISTRO_VARIANT}' \
    if bb.data.inherits_class('webos_distro_variant_dep', d) and not bb.data.inherits_class('native', d) else \
    '' \
}"
EXTRA_OECMAKE_DISTRO_VARIANT[vardepvalue] = "${EXTRA_OECMAKE_DISTRO_VARIANT}"
EXTRA_OECMAKE += "${EXTRA_OECMAKE_DISTRO_VARIANT}"

# This information is always useful to have around
EXTRA_OECMAKE += "-Wdev"

# Fixup in case CMake files don't recognize the new value i586 for
# CMAKE_SYSTEM_PROCESSOR (e.g. nodejs)
do_generate_toolchain_file:append() {
    sed '/CMAKE_SYSTEM_PROCESSOR/ s/i586/i686/' -i ${WORKDIR}/toolchain.cmake
}

# Record how cmake was invoked
do_configure:append() {
    # Keep in sync with how cmake_do_configure() invokes cmake
    echo $(which cmake) \
      ${OECMAKE_SITEFILE} \
      ${S} \
      -DCMAKE_INSTALL_PREFIX:PATH=${prefix} \
      -DCMAKE_INSTALL_SO_NO_EXE=0 \
      -DCMAKE_TOOLCHAIN_FILE=${WORKDIR}/toolchain.cmake \
      -DCMAKE_VERBOSE_MAKEFILE=1 \
      ${EXTRA_OECMAKE} \
      -Wno-dev > ${WORKDIR}/cmake.status
}

# Used in webOS.cmake _webos_set_from_env
export webos_accttemplatesdir
export webos_applicationsdir
export webos_bootdir
export webos_browserpluginsdir
export webos_browserstoragedir
export webos_cryptofsdir
export webos_customizationdir
export webos_db8datadir
export webos_db8mediadir
export webos_defaultconfdir
export webos_developerdir
export webos_downloaded_applicationsdir
export webos_downloadeddir
export webos_downloaded_frameworksdir
export webos_downloaded_pluginsdir
export webos_downloaded_servicesdir
export webos_emulatorshareddir
export webos_execstatedir
export webos_filecachedir
export webos_firmwaredir
export webos_firstusesentinelfile
export webos_frameworksdir
export webos_homedir
export webos_keysdir
export webos_localstatedir
export webos_logdir
export webos_mediadir
export webos_mntdir
export webos_mountablestoragedir
export webos_persistentstoragedir
export webos_pkgconfigdir
export webos_pluginsdir
export webos_preferencesdir
export webos_prefix
export webos_preservedtmpdir
export webos_qtpluginsdir
export webos_runtimeinfodir
export webos_servicesdir
export webos_settingsservice_errorsentinelfile
export webos_soundsdir
export webos_srcdir
export webos_sysbus_apipermissionsdir
export webos_sysbus_containersdir
export webos_sysbus_datadir
export webos_sysbus_devapipermissionsdir
export webos_sysbus_devdatadir
export webos_sysbus_devmanifestsdir
export webos_sysbus_devpermissionsdir
export webos_sysbus_devprvrolesdir
export webos_sysbus_devprvservicesdir
export webos_sysbus_devpubrolesdir
export webos_sysbus_devpubservicesdir
export webos_sysbus_devrolesdir
export webos_sysbus_devservicesdir
export webos_sysbus_dynapipermissionsdir
export webos_sysbus_dyndatadir
export webos_sysbus_dynmanifestsdir
export webos_sysbus_dynpermissionsdir
export webos_sysbus_dynprvrolesdir
export webos_sysbus_dynprvservicesdir
export webos_sysbus_dynpubrolesdir
export webos_sysbus_dynpubservicesdir
export webos_sysbus_dynrolesdir
export webos_sysbus_dynservicesdir
export webos_sysbus_groupsdir
export webos_sysbus_manifestsdir
export webos_sysbus_permissionsdir
export webos_sysbus_prvrolesdir
export webos_sysbus_prvservicesdir
export webos_sysbus_pubrolesdir
export webos_sysbus_pubservicesdir
export webos_sysbus_rolesdir
export webos_sysbus_servicesdir
export webos_sysconfdir
export webos_sysmgr_datadir
export webos_sysmgrdir
export webos_sysmgr_localstatedir
export webos_testsdir
export webos_udevscriptsdir
export webos_upstartconfdir
