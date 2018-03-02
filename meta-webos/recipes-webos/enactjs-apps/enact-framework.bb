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

PV = "1.13.3"
SRCREV = "bbb55faaf5fd03684f9f8d93596c40113e5836b7"

# Ordered dependency list for Enact; provides shrink-wrap style locking in of package versions
# Generated via https://gecko.lgsvl.com/jenkins/view/Enyo/job/enact-dependency-list/
WEBOS_ENACT_DEPENDENCIES ??= "\
    classnames@2.2.5 \
    js-tokens@3.0.2 \
    loose-envify@1.3.1 \
    invariant@2.2.2 \
    core-js@1.2.7 \
    iconv-lite@0.4.19 \
    encoding@0.1.12 \
    is-stream@1.1.0 \
    node-fetch@1.7.3 \
    whatwg-fetch@2.0.3 \
    isomorphic-fetch@2.2.1 \
    object-assign@4.1.1 \
    asap@2.0.6 \
    promise@7.3.1 \
    setimmediate@1.0.5 \
    ua-parser-js@0.7.17 \
    fbjs@0.8.16 \
    prop-types@15.6.0 \
    ramda@0.24.1 \
    create-react-class@15.6.2 \
    react@15.6.2 \
    react-dom@15.6.2 \
    change-emitter@0.1.6 \
    hoist-non-react-statics@1.2.0 \
    symbol-observable@1.1.0 \
    recompose@0.23.5 \
    warning@3.0.0 \
    eases@1.0.8 \
    dom-walk@0.1.1 \
    min-document@2.19.0 \
    process@0.5.2 \
    global@4.3.2 \
    is-function@1.0.1 \
    for-each@0.3.2 \
    trim@0.0.1 \
    parse-headers@2.0.1 \
    xtend@4.0.1 \
    xhr@2.4.1 \
"

# NOTE: We only need to bump PR if we change something OTHER than
# PV, SRCREV or the dependencies statement above.

PR = "r2"

# Skip unneeded tasks
do_configure[noexec] = "1"

do_compile() {
    working=$(pwd)
    cd ${S}

    sed -i -e "s/\(local([^)]*)\)[^;]*;/\1;/" enact/packages/moonstone/styles/fonts.less

    mkdir -p node_modules/@enact
    rm -fr enact/packages/sampler

    ${ENACT_NPM} install --prefix=. --loglevel=error ${WEBOS_ENACT_DEPENDENCIES}
    cp -fr enact/packages/* node_modules/@enact

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
