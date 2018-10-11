# Copyright (c) 2017-2018 LG Electronics, Inc.

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

PV = "2.2.1"
SRCREV = "4b4a834dfb156d40e0373d016e5a69f8a278e71c"

# Ordered dependency list for Enact; provides shrink-wrap style locking in of package versions
# Generated via https://gecko.lgsvl.com/jenkins/view/Enyo/job/enact-dependency-list/
WEBOS_ENACT_DEPENDENCIES ??= "\
    classnames@2.2.6 \
    js-tokens@4.0.0 \
    loose-envify@1.4.0 \
    invariant@2.2.4 \
    object-assign@4.1.1 \
    prop-types@15.6.2 \
    ramda@0.24.1 \
    schedule@0.5.0 \
    react@16.5.2 \
    react-dom@16.5.2 \
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
    ua-parser-js@0.7.18 \
    fbjs@0.8.17 \
    hoist-non-react-statics@2.5.5 \
    symbol-observable@1.2.0 \
    recompose@0.26.0 \
    direction@1.0.2 \
    warning@3.0.0 \
    eases@1.0.8 \
    dom-walk@0.1.1 \
    min-document@2.19.0 \
    process@0.5.2 \
    global@4.3.2 \
    is-function@1.0.1 \
    is-callable@1.1.4 \
    for-each@0.3.3 \
    trim@0.0.1 \
    parse-headers@2.0.1 \
    xtend@4.0.1 \
    xhr@2.5.0 \
"

# NOTE: We only need to bump PR if we change something OTHER than
# PV, SRCREV or the dependencies statement above.

PR = "r3"

# Skip unneeded tasks
do_configure[noexec] = "1"

do_compile() {
    working=$(pwd)
    cd ${S}

    rm -fr node_modules
    mkdir -p node_modules/@enact

    sed -i -e "s/\(local([^)]*)\)[^;]*;/\1;/" enact/packages/moonstone/styles/fonts.less
    for LIB in core ui moonstone spotlight i18n webos ; do
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
