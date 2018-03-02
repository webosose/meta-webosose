# Copyright (c) 2013-2018 LG Electronics, Inc.

inherit qmake5
inherit webos_filesystem_paths

# These are used in the luna-sysmgr recipe
export QT_CONFIGURE_PREFIX_PATH = "${OE_QMAKE_PATH_PREFIX}"
export QT_CONFIGURE_HEADERS_PATH = "${OE_QMAKE_PATH_QT_HEADERS}"
export QT_CONFIGURE_LIBRARIES_PATH = "${OE_QMAKE_PATH_LIBS}"
export QT_CONFIGURE_BINARIES_PATH = "${OE_QMAKE_PATH_EXTERNAL_HOST_BINS}"

# This is used in the webappmanager recipes
export STAGING_INCDIR

# Set webOS specific locations for .pr* files to access
EXTRA_QMAKEVARS_PRE += "\
    WEBOS_STAGING_INCDIR=${STAGING_INCDIR} \
"
# Used by mkspecs/features/webos-variables.prf
EXTRA_QMAKEVARS_PRE += "\
    WEBOS_INSTALL_BINS=${bindir} \
    WEBOS_INSTALL_LIBS=${libdir} \
    WEBOS_INSTALL_HEADERS=${includedir}/ \
    WEBOS_INSTALL_QML=${OE_QMAKE_PATH_QML} \
    WEBOS_INSTALL_QTPLUGINSDIR=${webos_qtpluginsdir} \
    WEBOS_INSTALL_WEBOS_APPLICATIONSDIR=${webos_applicationsdir} \
    WEBOS_PREFERENCESDIR=${webos_preferencesdir} \
    WEBOS_INSTALL_ROOT=${base_prefix} \
"
# webos-variables.prf is using default value of ${prefix}, but here we use ${OE_QMAKE_PATH_PREFIX}
EXTRA_QMAKEVARS_PRE += "\
    WEBOS_INSTALL_PREFIX=${OE_QMAKE_PATH_PREFIX} \
    WEBOS_INSTALL_DATADIR=${datadir} \
    WEBOS_INSTALL_SYSCONFDIR=${sysconfdir} \
    WEBOS_INSTALL_SYSBUS_DATADIR=${sysbus_datadir} \
    WEBOS_INSTALL_UPSTARTCONFDIR=${webos_upstartconfdir} \
"
# webos-variables.prf is using default value of webos_sysbus_servicesdir
# but the rest of the system is using singular servicedir
# webosSet(WEBOS_INSTALL_SYSBUS_SERVICESDIR, $$(webos_sysbus_servicesdir), $$WEBOS_INSTALL_SYSBUS_DATADIR/services.d)
# meta-webos/classes/webos_filesystem_paths.bbclass:webos_sysbus_servicedir = "${webos_sysbus_datadir}/services.d"
# Set both here from webos_sysbus_servicedir until PLAT-9971 is fixed
EXTRA_QMAKEVARS_PRE += "\
    WEBOS_INSTALL_SYSBUS_SERVICEDIR=${webos_sysbus_servicedir} \
    WEBOS_INSTALL_SYSBUS_SERVICESDIR=${webos_sysbus_servicedir} \
    WEBOS_INSTALL_SYSBUS_PUBSERVICESDIR=${webos_sysbus_pubservicesdir} \
    WEBOS_INSTALL_SYSBUS_PRVSERVICESDIR=${webos_sysbus_prvservicesdir} \
    WEBOS_INSTALL_SYSBUS_ROLESDIR=${webos_sysbus_rolesdir} \
    WEBOS_INSTALL_SYSBUS_PUBROLESDIR=${webos_sysbus_pubrolesdir} \
    WEBOS_INSTALL_SYSBUS_PRVROLESDIR=${webos_sysbus_prvrolesdir} \
    WEBOS_INSTALL_SYSBUS_APIDIR=${webos_sysbus_apipermissionsdir} \
    WEBOS_INSTALL_SYSBUS_PERMDIR=${webos_sysbus_permissionsdir} \
    WEBOS_INSTALL_SYSBUS_GROUPDIR=${webos_sysbus_groupsdir} \
"

# this value is exported in do_configure, so that project file can select MACHINE_NAME
WEBOS_QMAKE_MACHINE_ACTUAL ?= "${MACHINE}"
WEBOS_QMAKE_MACHINE ?= "${WEBOS_QMAKE_MACHINE_ACTUAL}"
# this value is defined only for make through EXTRA_OEMAKE
WEBOS_QMAKE_TARGET ?= ""

# add only when WEBOS_QMAKE_MACHINE is defined (by default it equals MACHINE)
EXPORT_WEBOS_QMAKE_MACHINE += "${@ 'export MACHINE=${WEBOS_QMAKE_MACHINE}' if d.getVar('WEBOS_QMAKE_MACHINE', True) != '' and bb.data.inherits_class('webos_machine_dep', d) and not bb.data.inherits_class('native', d) else '' }"
EXPORT_WEBOS_QMAKE_MACHINE[vardepvalue] = "${EXPORT_WEBOS_QMAKE_MACHINE}"

# add only when WEBOS_QMAKE_TARGET is defined (by default it's empty)
EXPORT_WEBOS_QMAKE_TARGET = "${@ 'MACHINE=${WEBOS_QMAKE_TARGET}' if d.getVar('WEBOS_QMAKE_TARGET', True) != '' and bb.data.inherits_class('webos_machine_dep', d) and not bb.data.inherits_class('native', d) else '' }"
EXPORT_WEBOS_QMAKE_TARGET[vardepvalue] = "${EXPORT_WEBOS_QMAKE_TARGET}"

EXTRA_OEMAKE += "${EXPORT_WEBOS_QMAKE_TARGET}"

# Add the the native tool in the paths as some project require rcc
# to be available
WEBOS_EXTRA_PATH .= "${OE_QMAKE_PATH_EXTERNAL_HOST_BINS}:"

do_configure_prepend() {
  ${EXPORT_WEBOS_QMAKE_MACHINE}
}
