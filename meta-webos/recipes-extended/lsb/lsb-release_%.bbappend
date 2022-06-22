# Copyright (c) 2013-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos9"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

inherit webos_core_os_dep

SRC_URI += " \
    file://fix-lsb_release-to-work-if-there-are-parens-in-release-codename.patch \
    file://dist-update \
"

WEBOS_TARGET_CORE_OS ?= "undefined"
BUILD_INFO_FILE = "${DISTRO}-release"
BUILD_DISTRIB_ID = "${@'${WEBOS_TARGET_CORE_OS}'.capitalize()}"
TRIMED_DISTRO_VERSION = "${@oe.utils.trim_version('${DISTRO_VERSION}')}"

do_configure:append() {
    cp ${WORKDIR}/dist-update ${S}/dist-update
    if [ ! -z ${PACKAGE_FEED_URI} -a ! -z ${PACKAGE_FEED_BASE_PATH} ]; then
        sed -e "s#@PACKAGE_FEED_URI@#${PACKAGE_FEED_URI}#" -e "s#@PACKAGE_FEED_BASE_PATH@#${PACKAGE_FEED_BASE_PATH}#" -e"s#@PACKAGE_DISTRO@#${DISTRO}#" -e"s#@TRIMED_DISTRO_VERSION@#${TRIMED_DISTRO_VERSION}#" -i ${S}/dist-update
        URI="${@os.path.join('${PACKAGE_FEED_URI}','${PACKAGE_FEED_BASE_PATH}','${DISTRO}','${TRIMED_DISTRO_VERSION}')}"
        echo "src/gz ${TRIMED_DISTRO_VERSION} \"${URI}\"" > ${S}/${TRIMED_DISTRO_VERSION}.conf
    fi
}

do_install:append() {
    # Remove lsb-release file and directory created by parent recipe.
    rm -f ${D}${sysconfdir}/lsb-release
    rm -rf ${D}${sysconfdir}/lsb-release.d

    sed -i -e 's/^CHECKFIRST=.*/CHECKFIRST="\${sysconfdir}\/${BUILD_INFO_FILE}"/' ${D}${base_bindir}/lsb_release
    echo "${BUILD_DISTRIB_ID} release ${DISTRO_VERSION}-${WEBOS_DISTRO_BUILD_ID} (${WEBOS_DISTRO_RELEASE_CODENAME})" > ${D}${sysconfdir}/${BUILD_INFO_FILE}

    # install dist-update
    if [ ! -z ${PACKAGE_FEED_URI} -a ! -z ${PACKAGE_FEED_BASE_PATH} ]; then
        install -d ${D}${bindir}
        install -m 0755 ${S}/dist-update ${D}${bindir}
    fi

    # install ${TRIMED_DISTRO_VERSION}.conf
    if [ ! -z ${PACKAGE_FEED_URI} -a ! -z ${PACKAGE_FEED_BASE_PATH} ]; then
        install -d ${D}${sysconfdir}/opkg
        install -m 0644 ${S}/${TRIMED_DISTRO_VERSION}.conf ${D}${sysconfdir}/opkg
    fi
}
