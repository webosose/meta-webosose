# Copyright (c) 2018-2024 LG Electronics, Inc.

SUMMARY = "Enact Based Web Browser"
AUTHOR = "Luc Van Tran <luc2.tran@lge.com>"
SECTION = "webos/apps"
LICENSE = "LicenseRef-EnactBrowser-Evaluation"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=6e00eb832d81f89a0f47fac10db717c7 \
    file://oss-pkg-info.yaml;md5=72b3e3cef46e5ab3e175e5b515dc3b18 \
"

WEBOS_VERSION = "1.0.0-17.browsershell.5_a69ab22ef0086291a31c8043dd37cafc373abc85"
PR = "r21"

inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_enactjs_app
inherit webos_filesystem_paths
inherit webos_npm_env

WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"
WEBOS_SYSTEM_BUS_FILES_LOCATION = "${S}/files/sysbus"
WEBOS_SYSTEM_BUS_MANIFEST_TYPE = "PASS"

WEBOS_ENACTJS_PACK_OPTS = "--isomorphic --production"
WEBOS_ENACTJS_ILIB_OVERRIDE = ""
WEBOS_PREFERRED_GFX_IMAGE_FORMAT_ENABLED="0"

SUPPORT_BROWSERSHELL = "true"

WEBOS_ENACTJS_PACK_UIOVERLAY = "\
    && cd ../../uioverlay/ \
    && ${ENACT_DEV} pack ${WEBOS_ENACTJS_PACK_OPTS} -o ${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}/uioverlay \
    && cd ../samples/enact-based \
"
WEBOS_ENACTJS_PACK_FOR_BROWSERSHELL = "${@oe.utils.conditional('SUPPORT_BROWSERSHELL', \
   'true', '${WEBOS_ENACTJS_PACK_UIOVERLAY}', '', d)}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
WEBOS_ENACTJS_PROJECT_PATH = "./samples/enact-based"
WEBOS_ENACTJS_PACK_OVERRIDE = "\
    ${ENACT_DEV} pack ${WEBOS_ENACTJS_PACK_OPTS} -o ${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID} --verbose && \
    ${WEBOS_NODE_BIN} resbundler.js ${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID} && \
    rm -fr ${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}/resources && \
    rm -fr ${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}/node_modules/@enact/moonstone/resources && \
    ln -sfn /usr/share/javascript/ilib/localedata/ ${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}/ilibdata && \
    cp -R --no-dereference --preserve=mode,links -v webos-locale.js label.js background.js defaults.js manifest.json pdf.js ${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}/ && \
    ${WEBOS_NODE_BIN} extract-inline.js ${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID} \
"

WEBOS_ENACTJS_PACK_OVERRIDE += "\
    && cp -rf resources/ ${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}/resources \
    && sed -i -E 's/(useBuiltInErrorPages:) *false/\1 true/g' ${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}/defaults.js \
    && ./scripts/install-manifest.js --from=manifest.json --to=${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}/manifest.json --version_suffix=`git rev-parse HEAD` \
    ${WEBOS_ENACTJS_PACK_FOR_BROWSERSHELL} \
"

# Remove --production, because that causes
# http://gecko.lge.com/Errors/Details/119724
# Error: Cannot find module 'glob'
WEBOS_NPM_INSTALL_FLAGS = "--arch=${WEBOS_NPM_ARCH} --target_arch=${WEBOS_NPM_ARCH} --without-ssl --insecure --no-optional --verbose"

do_compile:prepend() {
    cd ${S}
}

do_install:prepend() {
    cd ${S}
}

do_compile:append() {
    if ${SUPPORT_BROWSERSHELL}; then
        # download npm libraries for uioverlay
        cd uioverlay
        rm -rf node_modules
        ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install
        git apply --no-index --verbose ../samples/enact-based/enact_agate_internal_l.patch
        cd ../
    fi

    cd samples/enact-based
    git apply --no-index --verbose enact_agate_internal_l.patch

    cd ../../

    ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install
    ${WEBOS_NODE_BIN} ./scripts/cli.js transpile
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

do_install:append() {
    install_acg_configuration
}

FILES:${PN} += "${webos_applicationsdir}"

# Workaround for network access issue during do_compile task
do_compile[network] = "1"

PR:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack2', '', d)}"
SRC_URI += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'file://com.webos.app.enactbrowser', '', d)} \
"

do_install[postfuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'install_smack_rules', '', d)}"

install_smack_rules (){
    install -d ${D}${sysconfdir}/smack/accesses.d
    install -v -m 0644 ${WORKDIR}/com.webos.app.enactbrowser ${D}${sysconfdir}/smack/accesses.d/com.webos.app.enactbrowser
}
