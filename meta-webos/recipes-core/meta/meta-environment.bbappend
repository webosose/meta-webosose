# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# Used by mkspecs/features/webos-variables.prf
toolchain_create_sdk_env_script_append() {
    echo "export WEBOS_INSTALL_BINS=${target_exec_prefix}/bin" >> $script
    echo "export WEBOS_INSTALL_LIBS=${target_libdir}" >> $script
    echo "export WEBOS_INSTALL_HEADERS=${target_includedir}" >> $script
    echo "export WEBOS_INSTALL_QML=${target_libdir}/qml" >> $script
    echo "export WEBOS_INSTALL_QTPLUGINSDIR=${target_libdir}/plugins" >> $script
    echo "export webos_applicationsdir=${target_prefix}/palm/applications" >> $script
    echo "export webos_preferencesdir=${target_prefix}/preferences" >> $script
}
