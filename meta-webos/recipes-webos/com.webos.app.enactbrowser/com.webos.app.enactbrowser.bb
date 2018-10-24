# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "Enact Based Web Browser"
AUTHOR = "Mikyung Kim <mikyung27.kim@lge.com>"
SECTION = "webos/apps"
LICENSE = "LicenseRef-EnactBrowser-Evaluation"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6e00eb832d81f89a0f47fac10db717c7"

WEBOS_VERSION = "1.0.0-11_97e7e85864b480d6f5b1136f34c302d2ba9048af"
PR = "r3"

inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_enactjs_app

WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE = "false"
WEBOS_ENACTJS_PACK_OPTS = "--isomorphic --production --snapshot"
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

FILES_${PN} += "${webos_applicationsdir}"
