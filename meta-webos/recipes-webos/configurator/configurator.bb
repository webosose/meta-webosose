# Copyright (c) 2012-2023 LG Electronics, Inc.

SUMMARY = "Creates the database schema for webOS apps"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "luna-service2 db8 glib-2.0 pmloglib"

WEBOS_VERSION = "3.0.0-11_ded3f968c2943ef77d81755e5bf7de088447651a"
PR = "r9"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_daemon
inherit webos_machine_impl_dep
inherit webos_distro_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://configurator-activity.service \
    file://configurator-db8.service \
    file://configurator-db8.sh \
"
S = "${WORKDIR}/git"
FILES:${PN} += "${webos_sysbus_datadir}"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system/
    install -v -m 0644 ${WORKDIR}/configurator-activity.service ${D}${sysconfdir}/systemd/system/
    install -v -m 0644 ${WORKDIR}/configurator-db8.service ${D}${sysconfdir}/systemd/system/
    install -d ${D}${sysconfdir}/systemd/system/scripts
    install -v -m 0755 ${WORKDIR}/configurator-db8.sh ${D}${sysconfdir}/systemd/system/scripts
}
