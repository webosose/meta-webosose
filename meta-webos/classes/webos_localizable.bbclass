# Copyright (c) 2013-2020 LG Electronics, Inc.
#
# Localization-related settings and tasks
#

LIB32_PREFIX ?= ""

WEBOS_LOCALIZATION_DEPENDS = "${@ '' if bb.data.inherits_class('webos_qt_localization', d) or bb.data.inherits_class('webos_arch_indep', d) else '${LIB32_PREFIX}libwebosi18n' }"
DEPENDS_append = " ${WEBOS_LOCALIZATION_DEPENDS}"

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

#
# You can use this to specify your own list of subdirectories that will be localized by
# localization-tool independently. The loc tool will produce a separate resources dir for
# each subdirectory and strings from each directory will not appear in the other subdirectory's
# resources unless they both happen to use the same string. You can specify relative paths to
# localizable subdirectories which are relative to the root of your component. For example,
# if you want to localize the directories 'legacy/version1' and 'legacy/version2' under a
# component separately, you would simply specify:
#
# WEBOS_LOCALIZATION_SUBDIRS="legacy/version1 legacy/version2"
#

WEBOS_LOCALIZATION_SUBDIRS ??= ""
WEBOS_LOCALIZATION_GENERATE_RESOURCES ?= "true"

addtask do_generate_webos_localization after do_configure before do_install
do_generate_webos_localization[depends] += "localization-tool-native:do_populate_sysroot"
# it's only needed for do_generate_webos_localization function and correctly added
# above, but add it to normal DEPENDS as well to ensure that it's included in
# recdeptask and then in webos-bom.json
WEBOS_LOCALIZATION_DEPENDS += "localization-tool-native"
WEBOS_LOCALIZATION_DATA_PATH ?= "${S}"
JAVANAME = "java-11-openjdk-amd64"

do_generate_webos_localization () {
    if "${WEBOS_LOCALIZATION_GENERATE_RESOURCES}" ; then

        translation_target_locales="$(echo ${WEBOS_LOCALIZATION_TARGET_LOCALES} | sed -e 's/^ *//g' -e 's/ *$//g' | sed -e 's/ \+/,/g')"
        # Do not process for HTML files
        webos_localization_options="--ignore-html ${WEBOS_LOCALIZATION_OPTIONS}"

        local origdir=$PWD
        cd ${S}

        if [ ! -z "${WEBOS_LOCALIZATION_SUBDIRS}" ] ; then
            subdirs="$(echo ${WEBOS_LOCALIZATION_SUBDIRS} | sed -e 's/^ *//g' -e 's/ *$//g' | sed -e 's/ \+/,/g')"
            webos_localization_options="${webos_localization_options} --subdirs=${subdirs}"
        fi

        bbnote "Generating localized files"
        bbnote "- buildloc ${webos_localization_options}"
        bbnote "- xliff file name : ${WEBOS_LOCALIZATION_XLIFF_BASENAME}"
        bbnote "- locales to localization : ${translation_target_locales}"
        bbnote "- Source location: ${S}"

        bbnote "- Using JAVA_HOME=/usr/lib/jvm/${JAVANAME}"

        JAVA_HOME=/usr/lib/jvm/${JAVANAME} \
            buildloc ${webos_localization_options} -n ${WEBOS_LOCALIZATION_XLIFF_BASENAME} -x ${WEBOS_LOCALIZATION_DATA_PATH} --list-of-locales ${translation_target_locales}
    else
        bbnote "Generating localized files was disabled by WEBOS_LOCALIZATION_GENERATE_RESOURCES variable"
    fi

}

# where to install ${S}/resources files, service components should change it to whatever directory they want
webos_localization_resources_dir ??= "${datadir}/localization/${BPN}/resources"

WEBOS_LOCALIZATION_INSTALL_RESOURCES ?= "true"

do_install_append() {
    if "${WEBOS_LOCALIZATION_INSTALL_RESOURCES}" ; then
        if ls ${S}/resources/* >/dev/null 2>/dev/null ; then
            bbnote "Installing localized files"
            install -d ${D}${webos_localization_resources_dir}
            cp -R --no-dereference --preserve=mode,links -v ${S}/resources/* ${D}${webos_localization_resources_dir}
            find ${D}${webos_localization_resources_dir} -name \*.xliff -exec rm -vf {} \;
            find ${D}${webos_localization_resources_dir} -name \*.qm -exec rm -vf {} \;
            find ${D}${webos_localization_resources_dir} -name \*.ts -exec rm -vf {} \;
            find ${D}${webos_localization_resources_dir} -type d -empty -delete
            chown -R root:root ${D}${webos_localization_resources_dir}
        else
            bbwarn "${PN} inherits webos_localizable, but doesn't have any localized files in ${S}/resources"
        fi
    else
        bbnote "Not installing localized files, because WEBOS_LOCALIZATION_INSTALL_RESOURCES was set to false"
    fi
}

PACKAGE_BEFORE_PN += "${PN}-localization"
RRECOMMENDS_${PN} += "${PN}-localization"
FILES_${PN}-localization += "${webos_localization_resources_dir}"
