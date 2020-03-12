# Copyright (c) 2018-2020 LG Electronics, Inc.

SUMMARY = "Enact Based Web Browser"
AUTHOR = "Mikyung Kim <mikyung27.kim@lge.com>"
SECTION = "webos/apps"
LICENSE = "LicenseRef-EnactBrowser-Evaluation"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6e00eb832d81f89a0f47fac10db717c7"

WEBOS_VERSION = "1.0.0-21_0b67a4f5cfa73d4e24e43e7915e48e4f9ee2b3e7"
PR = "r7"

inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_enactjs_app
inherit webos_filesystem_paths

WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"
WEBOS_SYSTEM_BUS_FILES_LOCATION = "${S}files/sysbus"
WEBOS_SYSTEM_BUS_MANIFEST_TYPE = "PASS"

WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE = "false"
WEBOS_ENACTJS_PACK_OPTS = "--isomorphic --production --snapshot"
WEBOS_ENACTJS_ILIB_OVERRIDE = ""
WEBOS_PREFERRED_GFX_IMAGE_FORMAT_ENABLED="0"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git/"
WEBOS_ENACTJS_PROJECT_PATH = "./samples/enact-based"
WEBOS_ENACTJS_PACK_OVERRIDE = "\
    ${ENACT_DEV} pack ${WEBOS_ENACTJS_PACK_OPTS} && \
    ${ENACT_NODE} resbundler.js dist && \
    rm -fr ./dist/resources && \
    rm -fr ./dist/node_modules/@enact/moonstone/resources && \
    cp -f webos-locale.js dist/webos-locale.js && \
    ln -sfn /usr/share/javascript/ilib/localedata/ ./dist/ilibdata && \
    cp -f label.js dist/ && \
    cp -f background.js dist/ && \
    cp -f defaults.js dist/ && \
    cp -f manifest.json dist/ && \
    ${ENACT_NODE} extract-inline.js ./dist \
"

do_compile_prepend() {
    # Portion of do_compile_prepend of webos_enactjs_env.bbclass,
    # since this prepend occurs before that and we need NPM usage
    export HOME=${WORKDIR}
    ${ENACT_NPM} set cache ${TMPDIR}/npm_cache
    ${ENACT_NPM} config set prefer-offline true
    ${ENACT_NPM} install
    ${ENACT_NODE} ./scripts/cli.js transpile
}

do_compile_append() {
    cd ${S}/${WEBOS_ENACTJS_PROJECT_PATH}
    ./scripts/disable-ilib.sh
}

install_acg_configuration() {
    # sysbus files *.json
    install -d ${D}${webos_sysbus_permissionsdir}
    install -d ${D}${webos_sysbus_rolesdir}
    install -d ${D}${webos_sysbus_manifestsdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${BPN}.perm.json ${D}${webos_sysbus_permissionsdir}/${BPN}.app.json
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${BPN}.role.json ${D}${webos_sysbus_rolesdir}/${BPN}.app.json
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${BPN}.manifest.json ${D}${webos_sysbus_manifestsdir}/${BPN}.json
}

do_install_append_webos() {
    install_acg_configuration
}

FILES_${PN} += "${webos_applicationsdir}"
