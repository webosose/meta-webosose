# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "Enact Based Web Browser"
AUTHOR = "Mikyung Kim <mikyung27.kim@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

WEBOS_VERSION = "1.0.0-8_c44d8a71c1f4f64971589bb93ed98eed66617449"
PR = "r1"

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
