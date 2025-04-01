# Copyright (c) 2019-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos3"

# Used by mkspecs/features/webos-variables.prf
toolchain_create_sdk_env_script:append() {
    echo 'export TUNE_FEATURES="${TUNE_FEATURES}"' >> $script
    echo "export WEBOS_INSTALL_BINS=${target_exec_prefix}/bin" >> $script
    echo "export WEBOS_INSTALL_LIBS=${target_libdir}" >> $script
    echo "export WEBOS_INSTALL_HEADERS=${target_includedir}" >> $script
    echo "export WEBOS_INSTALL_QML=${target_libdir}/qml" >> $script
    echo "export WEBOS_INSTALL_QTPLUGINSDIR=${target_libdir}/plugins" >> $script
    echo "export webos_applicationsdir=${target_prefix}/palm/applications" >> $script
    echo "export webos_preferencesdir=${target_prefix}/preferences" >> $script
}

# Append to function from meta/classes/toolchain-scripts-base.bbclass
# to add more information from our WEBOS_SDK_BASELINE_* variables
toolchain_create_sdk_version:append() {
    echo "" >> $versionfile
    echo "SDK version: ${SDK_VERSION}" >> $versionfile
    echo "" >> $versionfile
    echo "Cross Toolchain Specification:" >> $versionfile
    echo "gcc: ${WEBOS_SDK_BASELINE_GCC}" >> $versionfile
    echo "binutils: ${WEBOS_SDK_BASELINE_BINUTILS}" >> $versionfile
    echo "${TCLIBC}: ${WEBOS_SDK_BASELINE_TCLIBC}" >> $versionfile
    echo "kernel headers: ${WEBOS_SDK_BASELINE_KERNEL_HEADERS}" >> $versionfile
    echo "" >> $versionfile
    echo "Other Tools:" >> $versionfile
    echo "gdb: ${WEBOS_SDK_BASELINE_GDB}" >> $versionfile
    echo "" >> $versionfile
    echo "webOS Components:" >> $versionfile
    echo "${DISTRO} ${WEBOS_SDK_BASELINE_BUILD_CODENAME} Build ${WEBOS_SDK_BASELINE_BUILD_ID} ${WEBOS_SDK_BASELINE_BUILD_DATE}" >> $versionfile
    echo "" >> $versionfile
    echo "Upstream Components:" >> $versionfile
    echo "Yocto ${WEBOS_SDK_BASELINE_YOCTO}" >> $versionfile
}
