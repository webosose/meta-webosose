# Copyright (c) 2012-2022 LG Electronics, Inc.

DESCRIPTION = "webOS component for managing network connections using connman"
AUTHOR = "Seokhee Lee <seokhee.lee@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=2763f3ed850f8412903ea776e0526bea \
    file://oss-pkg-info.yaml;md5=b0cf0d697c8340cbfa56b94bdc2539fb \
"

SECTION = "webos/services"

DEPENDS = "luna-service2 libpbnjson glib-2.0 luna-prefs openssl glib-2.0-native wca-support-api wca-support"
RDEPENDS:${PN} = "connman connman-client"

WEBOS_VERSION = "1.1.0-39_dc622623142035004f2354cc90cfee0e8fc3c5b3"
PR = "r9"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_machine_dep

# Set EXTRA_OECMAKE in webos-connman-adapter.bbappend to override default value for wifi and wired interfaces, for eg.
# EXTRA_OECMAKE += "-DWIFI_IFACE_NAME=wlan0 -DWIRED_IFACE_NAME=eth1"

EXTRA_OECMAKE += "-DENABLE_SCAN_ON_SOFTAP=true"

PACKAGECONFIG[enable-multiple-routing-table] = "-DMULTIPLE_ROUTING_TABLE:BOOL=true,-DMULTIPLE_ROUTING_TABLE:BOOL=false,"
PACKAGECONFIG = "enable-multiple-routing-table"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
SRC_URI:append:raspberrypi4 = " file://blacklistcdc_ether.conf"
S = "${WORKDIR}/git"

do_install:append:raspberrypi4 () {
    install -d  ${D}${sysconfdir}/modprobe.d
    install -m 644 ${WORKDIR}/blacklistcdc_ether.conf  ${D}${sysconfdir}/modprobe.d/blacklistcdc_ether.conf
}

FILES:${PN}:append:raspberrypi4 = " ${sysconfdir}/modprobe.d/*"
