# Copyright (c) 2014-2024 LG Electronics, Inc.

SUMMARY = "webOS Bluetooth SIL implementation for bluez5"
AUTHOR = "Muralidhar N <muralidhar.n@lge.com>"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=2763f3ed850f8412903ea776e0526bea \
    file://oss-pkg-info.yaml;md5=92fcba59ec6480ce73cd35edd7995099 \
"

DEPENDS = "glib-2.0 pmloglib glib-2.0-native"
RDEPENDS:${PN} += "bluez5"

# Handle case where it hasn't been set in DISTRO.conf/MACHINE.conf .
WEBOS_BLUETOOTH_ENABLED_SERVICE_CLASSES ??= ""
# Add runtime dependency on bluez5 OBEX service when we have to support FTP
RDEPENDS:${PN} += "${@ bb.utils.contains('WEBOS_BLUETOOTH_ENABLED_SERVICE_CLASSES', 'FTP', 'bluez5-obex', '', d)}"

WEBOS_VERSION = "0.1.0-87_91ed8541e63b298cda488585595a782b095da90c"
PR = "r12"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_bluetooth_sil

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# [WRQ-14472] bluetooth: Resolve buildpaths QA warnings
# http://gecko.lge.com:8000/Errors/Details/894426
# ERROR: QA Issue: File /usr/src/debug/bluetooth-sil-bluez5/0.1.0-86/Configured/src/freedesktop-interface.c in package bluetooth-sil-bluez5-src contains reference to TMPDIR
# File /usr/src/debug/bluetooth-sil-bluez5/0.1.0-86/Configured/src/bluez-interface.c in package bluetooth-sil-bluez5-src contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
