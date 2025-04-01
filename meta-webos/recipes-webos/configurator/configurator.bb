# Copyright (c) 2012-2025 LG Electronics, Inc.

SUMMARY = "Creates the database schema for webOS apps"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "luna-service2 db8 glib-2.0 pmloglib"

WEBOS_VERSION = "3.0.0-15_d3788f2dacab7f7576cd2c2d44c2b6e13eb8f712"
PR = "r11"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_daemon
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
FILES:${PN} += "${webos_sysbus_datadir}"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "configurator-activity.service configurator-db8.service"
WEBOS_SYSTEMD_SCRIPT ="configurator-db8.sh"

do_install:append() {
# All service files will be managed in meta-lg-webos.
# The service file in the repository is not used, so please delete it.
# See the page below for more details.
# http://collab.lge.com/main/pages/viewpage.action?pageId=2031668745
    rm ${D}${sysconfdir}/systemd/system/configurator-activity.service
    rm ${D}${sysconfdir}/systemd/system/configurator-db8.service
    rm ${D}${sysconfdir}/systemd/system/scripts/configurator-db8.sh
}

PACKAGECONFIG ??= ""
PACKAGECONFIG[extension-starfish] = "-DEXTENSION_STARFISH:BOOL=True,-DEXTENSION_STARFISH:BOOL=False,"
PACKAGECONFIG[no-first-use] = "-DNO_FIRST_USE:BOOL=True,-DNO_FIRST_USE:BOOL=False,"
PACKAGECONFIG:append:webos = " no-first-use"
