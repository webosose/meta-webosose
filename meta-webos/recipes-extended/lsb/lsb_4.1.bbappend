# Copyright (c) 2013-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos6"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

inherit webos_core_os_dep

SRC_URI += "file://fix-lsb_release-to-work-if-there-are-parens-in-release-codename.patch"

WEBOS_TARGET_CORE_OS ?= "undefined"
BUILD_INFO_FILE = "${DISTRO}-release"
BUILD_DISTRIB_ID = "${@'${WEBOS_TARGET_CORE_OS}'.capitalize()}"

# this might be needed by lsb_release (parent recipe says so), but lsbinitscripts conflict with our initscripts
RDEPENDS_${PN}_remove = "lsbinitscripts"

do_install_append() {
    # Remove lsb-release file and directory created by parent recipe.
    rm -f ${D}${sysconfdir}/lsb-release
    rm -rf ${D}${sysconfdir}/lsb-release.d

    echo "${BUILD_DISTRIB_ID} release ${DISTRO_VERSION}-${WEBOS_DISTRO_BUILD_ID} (${WEBOS_DISTRO_RELEASE_CODENAME})" > ${D}${sysconfdir}/${BUILD_INFO_FILE}
}
