# Copyright (c) 2020-2022 LG Electronics, Inc.

SUMMARY = "Enact Sandstone standard override used for Enact apps"
AUTHOR = "Seungho Park <seunghoh.park@lge.com>"
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

PV = "2.5.2"

SRCREV = "8d0f11ce16f9280fe511dddf5b7d071f4d35d7ea"
SRCREV_enact = "6e6668d3327d6993b0e80edb73c0fc90765c834e"

do_fetch[vardeps] += "SRCREV_enact"
SRCREV_FORMAT = "main_enact"

# Ordered dependency list for Sandstone; provides shrink-wrap style locking in of package versions
WEBOS_ENACT_DEPENDENCIES ??= "\
    classnames@2.3.1 \
    direction@1.0.4 \
    dom-walk@0.1.2 \
    global@4.4.0 \
    ilib@14.15.1 \
    invariant@2.2.4 \
    is-function@1.0.2 \
    js-tokens@4.0.0 \
    loose-envify@1.4.0 \
    min-document@2.19.0 \
    object-assign@4.1.1 \
    parse-headers@2.0.5 \
    process@0.11.10 \
    prop-types@15.8.1 \
    react@18.2.0 \
    react-dom@18.2.0 \
    react-is@18.2.0 \
    scheduler@0.23.0 \
    warning@4.0.3 \
    xhr@2.6.0 \
    xtend@4.0.2 \
"

# NOTE: We only need to bump PR if we change something OTHER than
# PV, SRCREV or the dependencies statement above.

PR = "r11"

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

sysroot_stage_all:append() {
    # files don't get staged by default so we must force /opt to be staged
    sysroot_stage_dir ${D}${datadir} ${SYSROOT_DESTDIR}${datadir}
}

FILES:${PN} += "${datadir}"

# Workaround for network access issue during do_compile task
do_compile[network] = "1"
