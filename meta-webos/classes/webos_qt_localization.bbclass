# Copyright (c) 2013-2022 LG Electronics, Inc.
#
# Localization-related settings and tasks for Qt apps
#

inherit webos_localizable
inherit qt6-paths

WEBOS_QT_LOCALIZATION_DEPENDS = "qttools-native"
DEPENDS:append = " ${WEBOS_QT_LOCALIZATION_DEPENDS}"

#
# Allow the default localization PN of the app to be overridden. The default is
# the value of of PN.
#
WEBOS_QT_LOCALIZATION_PN ?= "${BPN}"

#
# Allow the default basename of the QM files to be overridden. The default is
# the value of the last dot-separated field of WEBOS_QT_LOCALIZATION_PN.
#
WEBOS_QT_LOCALIZATION_QM_BASENAME ?= "${@ '${WEBOS_QT_LOCALIZATION_PN}'.split('.')[-1] }"

#
# Qt application resources sholud not be optimized, so that it needs 't' options
#
WEBOS_LOCALIZATION_OPTIONS:append = " -t"
#

#
# Allow the default subdirectory where the QM files are located to be overridden.
# The default is WEBOS_QT_LOCALIZATION_PN.
#
WEBOS_QT_LOCALIZATION_QM_SUBDIR ?= "${WEBOS_QT_LOCALIZATION_PN}"

webos_qt_localization_qm_dir = "${datadir}/qml/locales/${WEBOS_QT_LOCALIZATION_QM_SUBDIR}"
webos_qt_localization_ts_dir = "${webos_qt_localization_qm_dir}/ts"

#
# Generate QT localization data, and install them in the image.
#
addtask do_generate_webos_qt_localization after do_install before do_populate_sysroot do_package
do_generate_webos_qt_localization[depends] += "qttools-native:do_populate_sysroot"
fakeroot do_generate_webos_qt_localization() {
    bbnote "Generate QT localization data"
    # Generate .qm files
    local tsfiles
    tsfiles=`find ${S} -name "*.ts"`

    local tsfile
    for tsfile in $tsfiles
    do
        # Use (.*) so that the last instance of WEBOS_LOCALIZATION_XLIFF_BASENAME
        # is matched -- remember that S is an absolute path.
        local qmfile=`echo $tsfile | sed -r -e 's@(.*)/${WEBOS_LOCALIZATION_XLIFF_BASENAME}@\1/${WEBOS_QT_LOCALIZATION_QM_BASENAME}@' -e 's@\.ts@.qm@'`

        ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/lrelease $tsfile -qm $qmfile
    done
    bbnote "Done generating QT localization data"

    bbnote "Install QT localization data"
    local qmfiles
    qmfiles=`find ${S} -name "*.qm"`
    if [ -z "$qmfiles" ] ; then
        bbfatal "${PN} inherits webos_qt_localization, but doesn't have any localized .qm files"
    else
        install -d ${D}${webos_qt_localization_ts_dir}
        for tsfile in $tsfiles
        do
            install -v -m 0644 $tsfile ${D}${webos_qt_localization_ts_dir}
        done
        chown -R root:root ${D}${webos_qt_localization_ts_dir}

        install -d ${D}${webos_qt_localization_qm_dir}
        local qmfile
        for qmfile in $qmfiles
        do
            install -v -m 0644 $qmfile ${D}${webos_qt_localization_qm_dir}
        done
        chown -R root:root ${D}${webos_qt_localization_qm_dir}
    fi

    bbnote "Done installing QT localization files"
}

# Most QT recipes don't need the resources to be installed
WEBOS_LOCALIZATION_INSTALL_RESOURCES = "false"

FILES:${PN}-localization += "${webos_qt_localization_qm_dir}/*.qm"

PACKAGES += "${PN}-localization-ts"
FILES:${PN}-localization-ts += "${webos_qt_localization_ts_dir}"
