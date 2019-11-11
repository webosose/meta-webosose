# Copyright (c) 2017-2019 LG Electronics, Inc.

SUMMARY = "Enact framework standard override used for Enact apps"
AUTHOR = "Jason Robitaille <jason.robitaille@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit webos_enact_repo
inherit webos_arch_indep
inherit webos_enactjs_env

S = "${WORKDIR}/git"

SRC_URI = "${ENACTJS_GIT_REPO}/enact.git;nobranch=1;destsuffix=git/enact"

# NOTE: PV is the Enact version (which uses the Semantic Versioning spec),
# with the hyphen converted to a tilde so that the dpkg-style version-comparison
# algorithm properly recognizes that a pre-release precedes the associated final
# release (e.g., 1.0-pre.1 < 1.0).

PV = "3.2.4"
SRCREV = "170e35cb472bb0ff73e9b5187dd653ced254438d"

# Ordered dependency list for Enact; provides shrink-wrap style locking in of package versions
WEBOS_ENACT_DEPENDENCIES ??= "\
    classnames@2.2.6 \
    js-tokens@4.0.0 \
    loose-envify@1.4.0 \
    invariant@2.2.4 \
    object-assign@4.1.1 \
    react-is@16.8.6 \
    prop-types@15.7.2 \
    ramda@0.24.1 \
    scheduler@0.13.6 \
    react@16.8.6 \
    react-dom@16.8.6 \
    change-emitter@0.1.6 \
    core-js@1.2.7 \
    safer-buffer@2.1.2 \
    iconv-lite@0.4.24 \
    encoding@0.1.12 \
    is-stream@1.1.0 \
    node-fetch@1.7.3 \
    whatwg-fetch@3.0.0 \
    isomorphic-fetch@2.2.1 \
    asap@2.0.6 \
    promise@7.3.1 \
    setimmediate@1.0.5 \
    ua-parser-js@0.7.19 \
    fbjs@0.8.17 \
    hoist-non-react-statics@2.5.5 \
    symbol-observable@1.2.0 \
    recompose@0.26.0 \
    warning@3.0.0 \
    direction@1.0.3 \
    eases@1.0.8 \
    dom-walk@0.1.1 \
    min-document@2.19.0 \
    process@0.5.2 \
    global@4.3.2 \
    is-function@1.0.1 \
    is-callable@1.1.4 \
    for-each@0.3.3 \
    object-keys@1.1.1 \
    define-properties@1.1.3 \
    is-date-object@1.0.1 \
    has-symbols@1.0.0 \
    is-symbol@1.0.2 \
    es-to-primitive@1.2.0 \
    function-bind@1.1.1 \
    has@1.0.3 \
    is-regex@1.0.4 \
    es-abstract@1.13.0 \
    string.prototype.trim@1.1.2 \
    parse-headers@2.0.2 \
    xtend@4.0.1 \
    xhr@2.5.0 \
    ilib@14.4.0 \
"

# NOTE: We only need to bump PR if we change something OTHER than
# PV, SRCREV or the dependencies statement above.

PR = "r5"

# Skip unneeded tasks
do_configure[noexec] = "1"

do_compile() {
    working=$(pwd)
    cd ${S}

    rm -fr node_modules
    mkdir -p node_modules/@enact

    # Update any fonts to exclude unneeded files
    if [ -f enact/packages/moonstone/styles/fonts.less ] ; then
        sed -i -e "s/[, ]*url([^)]*.ttf['\"]*)[^,;]*//" enact/packages/moonstone/styles/fonts.less
    fi
    if [ -f enact/packages/moonstone/styles/internal/fonts.less ] ; then
        sed -i -e "s/[, ]*url([^)]*.ttf['\"]*)[^,;]*//" enact/packages/moonstone/styles/internal/fonts.less
    fi

    for LIB in core ui moonstone spotlight i18n webos ; do
        cd ${S}/enact/packages/${LIB}
        mkdir node_modules
        ln -sfn ../.. node_modules/@enact
        ${ENACT_NODE} ${WEBOS_ENACTJS_TOOL_PATH}/node_binaries/enact-typedef.js
        cd ${S}
        ${ENACT_NPM} pack --loglevel=error ./enact/packages/${LIB}
    done

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
