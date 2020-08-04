# Copyright (c) 2014-2020 LG Electronics, Inc.

SUMMARY = "webOS Bluetooth SIL implementation for bluez5"
AUTHOR = "Sameeer Mulla <sameer.mulla@lge.com>"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=92fcba59ec6480ce73cd35edd7995099 \
"

DEPENDS = "glib-2.0 pmloglib glib-2.0-native"
RDEPENDS_${PN} += "bluez5"

# Handle case where it hasn't been set in DISTRO.conf/MACHINE.conf .
WEBOS_BLUETOOTH_ENABLED_SERVICE_CLASSES ??= ""
# Add runtime dependency on bluez5 OBEX service when we have to support FTP
RDEPENDS_${PN} += "${@ bb.utils.contains('WEBOS_BLUETOOTH_ENABLED_SERVICE_CLASSES', 'FTP', 'bluez5-obex', '', d)}"

WEBOS_VERSION = "0.1.0-36_a138b07e086c1fc7d0096838d0d52f6ded5a7fe8"
PR = "r2"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_distro_dep
inherit webos_bluetooth_sil

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# http://caprica.lgsvl.com:8080/Errors/Details/1092093
# bluetooth-sil-bluez5/0.1.0-30-r4/git/src/bluez5advertise.cpp:191:41: error: format not a string literal and no format arguments [-Werror=format-security]
#  g_print(g_variant_print(arguments,TRUE));
#                                         ^
# bluetooth-sil-bluez5/0.1.0-30-r4/git/src/bluez5advertise.cpp:192:46: error: format not a string literal and no format arguments [-Werror=format-security]
#  g_print(g_variant_get_type_string(arguments));
#                                              ^
SECURITY_STRINGFORMAT = ""
