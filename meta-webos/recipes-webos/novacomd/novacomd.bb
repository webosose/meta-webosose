# Copyright (c) 2012-2018 LG Electronics, Inc.

SUMMARY = "NovaCOMd -- Daemon for NovaCOM (device and host)"
AUTHOR = "Steve Lemke <steve.lemke@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
SECTION = "webos/base"

WEBOS_VERSION = "2.0.0-1_2b9a22a1438afebe6e67dcf4db01b6464c605308"
PR = "r1"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_machine_impl_dep

DEPENDS = "${@oe.utils.conditional('WEBOS_TARGET_MACHINE_IMPL','host','libusb','nyx-lib',d)}"
RDEPENDS_${PN} = "${@oe.utils.conditional('WEBOS_TARGET_MACHINE_IMPL','emulator','iproute2','',d)}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://novacomd.conf \
"

do_install_append() {
    install -d ${D}${sysconfdir}/init
    install -m 0644 ${WORKDIR}/novacomd.conf ${D}${sysconfdir}/init/novacomd.conf
}
