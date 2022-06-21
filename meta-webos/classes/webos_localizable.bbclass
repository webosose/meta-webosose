# Copyright (c) 2013-2022 LG Electronics, Inc.
#
# Localization-related settings and tasks
#

LIB32_PREFIX ?= ""

WEBOS_LOCALIZATION_DEPENDS = "${@ '' if bb.data.inherits_class('webos_qt_localization', d) or bb.data.inherits_class('webos_arch_indep', d) else '${LIB32_PREFIX}libwebosi18n' }"
DEPENDS:append = " ${WEBOS_LOCALIZATION_DEPENDS}"

inherit webos_filesystem_paths

#
# Reference command and paths
#

WEBOS_LOCALIZATION_OPTIONS ??= ""

#
# Allow the default basename of the xliff file to be overridden. The default is
# the value of the last dot-separated field of PN.
#
WEBOS_LOCALIZATION_XLIFF_BASENAME ?= "${@ '${BPN}'.split('.')[-1] }"

#
# Locales to localization.
# The default is in meta-webos/conf/distro/include/webos-localization-languages.inc
#
WEBOS_LOCALIZATION_TARGET_LOCALES ?= ""
WEBOS_LOCALIZATION_GENERATE_RESOURCES ?= "true"

addtask do_generate_webos_localization after do_configure before do_install
do_generate_webos_localization[depends] += "localization-tool-native:do_populate_sysroot"
# it's only needed for do_generate_webos_localization function and correctly added
# above, but add it to normal DEPENDS as well to ensure that it's included in
# recdeptask and then in webos-bom.json
WEBOS_LOCALIZATION_DEPENDS += "localization-tool-native"
WEBOS_LOCALIZATION_DATA_PATH ?= "${S}"
WEBOS_LOCALIZATION_SOURCE_DIR ?= "${S}"
WEBOS_LOCALIZATION_SOURCE_RESOURCES ?= "${WEBOS_LOCALIZATION_SOURCE_DIR}/resources"
WEBOS_LOCALIZATION_SOURCE_RESOURCES_WITHOUT_TMPDIR ?= "${@d.getVar('WEBOS_LOCALIZATION_SOURCE_RESOURCES').replace(d.getVar('TMPDIR'), 'TMPDIR')}"

WEBOS_JS_LOCTOOL_PATH = "${STAGING_DIR_NATIVE}/opt/js-loctool"
WEBOS_JS_LOCTOOL = "${WEBOS_JS_LOCTOOL_PATH}/node_modules/loctool/loctool.js"

do_generate_webos_localization () {
    if "${WEBOS_LOCALIZATION_GENERATE_RESOURCES}" ; then
        translation_target_locales="$(echo ${WEBOS_LOCALIZATION_TARGET_LOCALES} | sed -e 's/^ *//g' -e 's/ *$//g' | sed -e 's/ \+/,/g')"

        webos_localization_options="-2 --localizeOnly --quiet --exclude oe-logs,oe-workdir --xliffStyle custom"

        if ${@ 'true' if bb.data.inherits_class('webos_qt_localization', d) else 'false' } ; then
            webos_localization_options="--pseudo ${webos_localization_options}"
        fi

        local origdir=$PWD
        cd ${WEBOS_LOCALIZATION_SOURCE_DIR}

        bbnote "Generating localized files"
        bbnote "- xliff file name : ${WEBOS_LOCALIZATION_XLIFF_BASENAME}"
        bbnote "- locales to localization : ${translation_target_locales}"

        node ${WEBOS_JS_LOCTOOL} -x ${WEBOS_LOCALIZATION_DATA_PATH}/${WEBOS_LOCALIZATION_XLIFF_BASENAME} -l ${translation_target_locales} ${webos_localization_options}

    else
        bbnote "Generating localized files was disabled by WEBOS_LOCALIZATION_GENERATE_RESOURCES variable"
    fi

}

# where to install ${S}/resources files, service components should change it to whatever directory they want
webos_localization_resources_dir ??= "${datadir}/localization/${BPN}/resources"

WEBOS_LOCALIZATION_INSTALL_RESOURCES ?= "true"

do_install:append() {
    if "${WEBOS_LOCALIZATION_INSTALL_RESOURCES}" ; then
        if ls ${WEBOS_LOCALIZATION_SOURCE_RESOURCES}/* >/dev/null 2>/dev/null ; then
            bbnote "Installing localized files"
            install -d ${D}${webos_localization_resources_dir}
            cp -R --no-dereference --preserve=mode,links -v ${WEBOS_LOCALIZATION_SOURCE_RESOURCES}/* ${D}${webos_localization_resources_dir}
            find ${D}${webos_localization_resources_dir} -name \*.xliff -exec rm -vf {} \;
            find ${D}${webos_localization_resources_dir} -name \*.qm -exec rm -vf {} \;
            find ${D}${webos_localization_resources_dir} -name \*.ts -exec rm -vf {} \;
            find ${D}${webos_localization_resources_dir} -type d -empty -delete
            chown -R root:root ${D}${webos_localization_resources_dir}
        else
            bbfatal "${PN} inherits webos_localizable, but doesn't have any localized files in ${WEBOS_LOCALIZATION_SOURCE_RESOURCES_WITHOUT_TMPDIR}"
        fi
    else
        bbnote "Not installing localized files, because WEBOS_LOCALIZATION_INSTALL_RESOURCES was set to false"
    fi
}

PACKAGE_BEFORE_PN += "${PN}-localization"
RRECOMMENDS:${PN} += "${PN}-localization"
FILES:${PN}-localization += "${webos_localization_resources_dir}"
