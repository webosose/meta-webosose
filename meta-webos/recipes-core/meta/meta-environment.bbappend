# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

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
