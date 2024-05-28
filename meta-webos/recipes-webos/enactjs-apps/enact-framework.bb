# Copyright (c) 2017-2024 LG Electronics, Inc.

# Maintained by Seungho Park <seunghoh.park@lge.com>
SUMMARY = "Enact moonstone standard override used for Enact apps"
AUTHOR = "EnactUnassigned <enact.swp@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://enact/LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

inherit webos_enact_repo
inherit webos_arch_indep
inherit webos_enactjs_env

S = "${WORKDIR}/git"

SRC_URI = " \
    ${ENACTJS_GIT_REPO}/moonstone.git;name=main${WEBOS_GIT_PROTOCOL};nobranch=1;destsuffix=git/moonstone \
    ${ENACTJS_GIT_REPO}/enact;name=enact${WEBOS_GIT_PROTOCOL};nobranch=1;destsuffix=git/enact \
"

# NOTE: PV is the Moonstone version (which uses the Semantic Versioning spec),
# with the hyphen converted to a tilde so that the dpkg-style version-comparison
# algorithm properly recognizes that a pre-release precedes the associated final
# release (e.g., 1.0-pre.1 < 1.0).

PV = "4.5.3"

SRCREV_main = "1cede54ee5df6f1a33d253eccaaf285fe1809bb6"
SRCREV_enact = "020e8fd4ad58352274ec85a1842eeab01f12dbca"

do_fetch[vardeps] += "SRCREV_enact"
SRCREV_FORMAT = "main_enact"

# Ordered dependency list for Moonstone; provides shrink-wrap style locking in of package versions
WEBOS_ENACT_DEPENDENCIES ??= "\
    classnames@2.3.2 \
    direction@1.0.4 \
    dom-walk@0.1.2 \
    global@4.4.0 \
    ilib@14.20.0 \
    invariant@2.2.4 \
    is-function@1.0.2 \
    js-tokens@4.0.0 \
    loose-envify@1.4.0 \
    min-document@2.19.0 \
    object-assign@4.1.1 \
    parse-headers@2.0.5 \
    process@0.11.10 \
    prop-types@15.8.1 \
    ramda@0.29.0 \
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

PR = "r27"

# Skip unneeded tasks
do_configure[noexec] = "1"

do_compile() {
    cd ${S}
    rm -fr node_modules
    mkdir node_modules

    for LIB in core ui spotlight i18n webos ; do
        cd ${S}/enact/packages/$LIB
        ${ENACT_JSDOC_TO_TS}
    done

    cd ${S}/moonstone

    # Update any fonts to exclude unneeded files
    if [ -f styles/fonts.less ] ; then
        sed -i -e "s/[, ]*url([^)]*.ttf['\"]*)[^,;]*//" styles/fonts.less
    fi
    if [ -f styles/internal/fonts.less ] ; then
        sed -i -e "s/[, ]*url([^)]*.ttf['\"]*)[^,;]*//" styles/internal/fonts.less
    fi

    ${ENACT_JSDOC_TO_TS}

    cd ${S}

    ${WEBOS_NPM_BIN} pack --loglevel=error ./moonstone
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

    cd ${working}
}

do_install() {
    install -d ${D}${datadir}/javascript/enact/@enact
    cp -R --no-dereference --preserve=mode,links -v ${S}/node_modules/* ${D}${datadir}/javascript/enact
}

SYSROOT_DIRS += "${datadir}"

FILES:${PN} += "${datadir}"

# Workaround for network access issue during do_compile task
do_compile[network] = "1"
