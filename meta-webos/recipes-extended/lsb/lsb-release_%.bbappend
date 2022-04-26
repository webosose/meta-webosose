# Copyright (c) 2013-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos7"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

inherit webos_core_os_dep

SRC_URI += "file://fix-lsb_release-to-work-if-there-are-parens-in-release-codename.patch"

WEBOS_TARGET_CORE_OS ?= "undefined"
BUILD_INFO_FILE = "${DISTRO}-release"
BUILD_DISTRIB_ID = "${@'${WEBOS_TARGET_CORE_OS}'.capitalize()}"

do_install:append() {
    # Remove lsb-release file and directory created by parent recipe.
    rm -f ${D}${sysconfdir}/lsb-release
    rm -rf ${D}${sysconfdir}/lsb-release.d

    sed -i -e 's/^CHECKFIRST=.*/CHECKFIRST="\${sysconfdir}\/${BUILD_INFO_FILE}"/' ${D}${base_bindir}/lsb_release
    echo "${BUILD_DISTRIB_ID} release ${DISTRO_VERSION}-${WEBOS_DISTRO_BUILD_ID} (${WEBOS_DISTRO_RELEASE_CODENAME})" > ${D}${sysconfdir}/${BUILD_INFO_FILE}
}
