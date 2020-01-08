# Copyright (c) 2013-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos7"

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

    sed -i -e 's/^CHECKFIRST=.*/CHECKFIRST="\${sysconfdir}\/${BUILD_INFO_FILE}"/' ${D}${base_bindir}/lsb_release
    echo "${BUILD_DISTRIB_ID} release ${DISTRO_VERSION}-${WEBOS_DISTRO_BUILD_ID} (${WEBOS_DISTRO_RELEASE_CODENAME})" > ${D}${sysconfdir}/${BUILD_INFO_FILE}
}

# lsb recipe was removed in Yocto 3.0 Zeus and replaced with just lsb-release in:
# commit fb064356af615d67d85b65942103bf943d84d290
# Author: Adrian Bunk <bunk@stusta.de>
# Date:   Sun Aug 25 20:21:15 2019 +0300
#
#     Remove LSB support
#
# but backported keyutils recipe from Zeus already RDEPENDS on lsb-release
# provide it here, so that we don't need to modify backported keyutils
# and then this part can be dropped when renaming this from lsb to lsb-release in:
# http://gpro.lge.com/254026
PROVIDES += "lsb-release"
RPROVIDES_${PN} += "lsb-release"
