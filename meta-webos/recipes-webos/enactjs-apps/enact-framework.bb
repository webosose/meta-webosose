# Copyright (c) 2017-2021 LG Electronics, Inc.

SUMMARY = "Enact moonstone standard override used for Enact apps"
AUTHOR = "Jason Robitaille <jason.robitaille@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit webos_enact_repo
inherit webos_arch_indep
inherit webos_enactjs_env

S = "${WORKDIR}/git"

SRC_URI = " \
    ${ENACTJS_GIT_REPO}/moonstone.git;name=main;nobranch=1;destsuffix=git/moonstone \
    ${ENACTJS_GIT_REPO}/enact;name=enact;nobranch=1;destsuffix=git/enact \
"

# NOTE: PV is the Moonstone version (which uses the Semantic Versioning spec),
# with the hyphen converted to a tilde so that the dpkg-style version-comparison
# algorithm properly recognizes that a pre-release precedes the associated final
# release (e.g., 1.0-pre.1 < 1.0).

PV = "3.2.5"

SRCREV_main = "8e86f0b41ef41e2b91651ff79d431627f7939d78"
SRCREV_enact = "71ff6d98d9b9c74a52908f835b73cf583fe52e1c"

do_fetch[vardeps] += "SRCREV_enact"
SRCREV_FORMAT = "main_enact"

# Ordered dependency list for Moonstone; provides shrink-wrap style locking in of package versions
WEBOS_ENACT_DEPENDENCIES ??= "\
    classnames@2.2.6 \
    js-tokens@4.0.0 \
    loose-envify@1.4.0 \
    invariant@2.2.4 \
    object-assign@4.1.1 \
    react-is@16.13.1 \
    prop-types@15.7.2 \
    ramda@0.24.1 \
    react@16.13.1 \
    scheduler@0.19.1 \
    react-dom@16.13.1 \
    change-emitter@0.1.6 \
    core-js@1.2.7 \
    safer-buffer@2.1.2 \
    iconv-lite@0.6.2 \
    encoding@0.1.13 \
    is-stream@1.1.0 \
    node-fetch@1.7.3 \
    whatwg-fetch@3.4.0 \
    isomorphic-fetch@2.2.1 \
    asap@2.0.6 \
    promise@7.3.1 \
    setimmediate@1.0.5 \
    ua-parser-js@0.7.21 \
    fbjs@0.8.17 \
    hoist-non-react-statics@2.5.5 \
    symbol-observable@1.2.0 \
    recompose@0.26.0 \
    warning@4.0.3 \
    dom-walk@0.1.2 \
    min-document@2.19.0 \
    process@0.5.2 \
    global@4.3.2 \
    is-function@1.0.2 \
    parse-headers@2.0.3 \
    xtend@4.0.2 \
    xhr@2.5.0 \
    ilib@14.6.2 \
    direction@1.0.4 \
"

# NOTE: We only need to bump PR if we change something OTHER than
# PV, SRCREV or the dependencies statement above.

PR = "r8"

# Skip unneeded tasks
do_configure[noexec] = "1"

do_compile() {
    cd ${S}
    rm -fr node_modules
    mkdir node_modules

    for LIB in core ui spotlight i18n webos ; do
        cd ${S}/enact/packages/$LIB
        ${ENACT_NODE} ${WEBOS_ENACTJS_TOOL_PATH}/node_binaries/enact-typedef.js
    done

    cd ${S}/moonstone

    # Update any fonts to exclude unneeded files
    if [ -f styles/fonts.less ] ; then
        sed -i -e "s/[, ]*url([^)]*.ttf['\"]*)[^,;]*//" styles/fonts.less
    fi
    if [ -f styles/internal/fonts.less ] ; then
        sed -i -e "s/[, ]*url([^)]*.ttf['\"]*)[^,;]*//" styles/internal/fonts.less
    fi

    ${ENACT_NODE} ${WEBOS_ENACTJS_TOOL_PATH}/node_binaries/enact-typedef.js

    cd ${S}

    ${ENACT_NPM} pack --loglevel=error ./moonstone
    ${ENACT_NPM} pack --loglevel=error ./enact/packages/core
    ${ENACT_NPM} pack --loglevel=error ./enact/packages/ui
    ${ENACT_NPM} pack --loglevel=error ./enact/packages/spotlight
    ${ENACT_NPM} pack --loglevel=error ./enact/packages/i18n
    ${ENACT_NPM} pack --loglevel=error ./enact/packages/webos
    ${ENACT_NPM} pack --loglevel=error ${WEBOS_ENACT_DEPENDENCIES}

    for ARCHIVE in $(find . -name "*.tgz") ; do
        tar --warning=no-unknown-keyword -xzf ${ARCHIVE} package/package.json
        PKG=$(${ENACT_NODE} -p "require('./package/package.json').name")
        mkdir -p node_modules/${PKG}
        mv -f ${ARCHIVE} node_modules/${PKG}/package.tgz
    done
    rm -fr package

    cd ${working}
}

do_install() {
    install -d ${D}${datadir}/javascript/enact/@enact
    cp -R --no-dereference --preserve=mode,links -v ${S}/node_modules/* ${D}${datadir}/javascript/enact
}

sysroot_stage_all_append() {
    # files don't get staged by default so we must force /opt to be staged
    sysroot_stage_dir ${D}${datadir} ${SYSROOT_DESTDIR}${datadir}
}

FILES_${PN} += "${datadir}"
