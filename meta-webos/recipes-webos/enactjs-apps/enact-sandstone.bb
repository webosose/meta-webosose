# Copyright (c) 2020-2021 LG Electronics, Inc.

SUMMARY = "Enact Sandstone standard override used for Enact apps"
AUTHOR = "Jason Robitaille <jason.robitaille@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://enact/LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

inherit webos_enact_repo
inherit webos_arch_indep
inherit webos_enactjs_env

S = "${WORKDIR}/git"

SRC_URI = " \
    ${ENACTJS_GIT_REPO}/sandstone.git;name=main${WEBOS_GIT_PROTOCOL};nobranch=1;destsuffix=git/sandstone \
    ${ENACTJS_GIT_REPO}/enact;name=enact${WEBOS_GIT_PROTOCOL};nobranch=1;destsuffix=git/enact \
"

# NOTE: PV is the Sandstone version (which uses the Semantic Versioning spec),
# with the hyphen converted to a tilde so that the dpkg-style version-comparison
# algorithm properly recognizes that a pre-release precedes the associated final
# release (e.g., 1.0-pre.1 < 1.0).

PV = "2.0.1"

SRCREV = "d82b4339adb6b89b060775fc12af0904c6eb8879"
SRCREV_enact = "e5c7a0c9d3b8d15321f5fff3c0d17bc0ae9a786e"

do_fetch[vardeps] += "SRCREV_enact"
SRCREV_FORMAT = "main_enact"

# Ordered dependency list for Sandstone; provides shrink-wrap style locking in of package versions
WEBOS_ENACT_DEPENDENCIES ??= "\
    asap@2.0.6 \
    change-emitter@0.1.6 \
    classnames@2.3.1 \
    core-js@1.2.7 \
    direction@1.0.4 \
    dom-walk@0.1.2 \
    encoding@0.1.13 \
    fbjs@0.8.17 \
    global@4.4.0 \
    hoist-non-react-statics@2.5.5 \
    iconv-lite@0.6.3 \
    ilib@14.9.2 \
    invariant@2.2.4 \
    is-function@1.0.2 \
    is-stream@1.1.0 \
    isomorphic-fetch@2.2.1 \
    js-tokens@4.0.0 \
    loose-envify@1.4.0 \
    min-document@2.19.0 \
    node-fetch@1.7.3 \
    object-assign@4.1.1 \
    parse-headers@2.0.4 \
    process@0.11.10 \
    promise@7.3.1 \
    prop-types@15.7.2 \
    ramda@0.24.1 \
    react@17.0.2 \
    react-dom@17.0.2 \
    react-is@17.0.2 \
    react-lifecycles-compat@3.0.4 \
    recompose@0.30.0 \
    regenerator-runtime@0.13.9 \
    safer-buffer@2.1.2 \
    scheduler@0.20.2 \
    setimmediate@1.0.5 \
    symbol-observable@1.2.0 \
    ua-parser-js@0.7.28 \
    warning@4.0.3 \
    whatwg-fetch@3.6.2 \
    xhr@2.6.0 \
    xtend@4.0.2 \
"

# NOTE: We only need to bump PR if we change something OTHER than
# PV, SRCREV or the dependencies statement above.

PR = "r6"

# Skip unneeded tasks
do_configure[noexec] = "1"

do_compile() {
    cd ${S}/sandstone
    ${ENACT_JSDOC_TO_TS}

    for LIB in core ui spotlight i18n webos ; do
        cd ${S}/enact/packages/$LIB
        ${ENACT_JSDOC_TO_TS}
    done

    cd ${S}

    # cleanup any previous do_compile
    rm -fr node_modules
    rm -fr package

    ${WEBOS_NPM_BIN} pack --loglevel=error ./sandstone
    ${WEBOS_NPM_BIN} pack --loglevel=error ./enact/packages/core
    ${WEBOS_NPM_BIN} pack --loglevel=error ./enact/packages/ui
    ${WEBOS_NPM_BIN} pack --loglevel=error ./enact/packages/spotlight
    ${WEBOS_NPM_BIN} pack --loglevel=error ./enact/packages/i18n
    ${WEBOS_NPM_BIN} pack --loglevel=error ./enact/packages/webos
    ${WEBOS_NPM_BIN} pack --loglevel=error ${WEBOS_ENACT_DEPENDENCIES}

    for ARCHIVE in $(find . -name "*.tgz") ; do
        tar --warning=no-unknown-keyword -xzf ${ARCHIVE} package/package.json
        PKG=$(${WEBOS_NODE_BIN} -p "require('./package/package.json').name")
        mkdir -p node_modules/${PKG}
        mv -f ${ARCHIVE} node_modules/${PKG}/package.tgz
    done
    rm -fr package
}

do_install() {
    install -d ${D}${datadir}/javascript/enact-sandstone/@enact
    cp -R --no-dereference --preserve=mode,links -v ${S}/node_modules/* ${D}${datadir}/javascript/enact-sandstone
}

sysroot_stage_all_append() {
    # files don't get staged by default so we must force /opt to be staged
    sysroot_stage_dir ${D}${datadir} ${SYSROOT_DESTDIR}${datadir}
}

FILES_${PN} += "${datadir}"
