# Copyright (c) 2014-2020 LG Electronics, Inc.

SUMMARY = "webOS Bluetooth management service"
AUTHOR = "Sameer Mulla <sameer.mulla@lge.com>"
SECTION = "webos/services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=1ff1242ee7d960d745e8abafd26f2471 \
"

DEPENDS = " \
    bluetooth-sil-api \
    glib-2.0 \
    libpbnjson \
    luna-service2 \
"

# Set the default Bluetooth SIL that the service will dynamically load to the
# specified by BLUETOOTH_SIL_NAME
BLUETOOTH_SIL_NAME ?= "bluez5"
BLUETOOTH_SIL_COMPONENT = "${@ ' bluetooth-sil-'.join([''] + '${BLUETOOTH_SIL_NAME}'.split(' '))}"
RDEPENDS_${PN} = " \
    ${BLUETOOTH_SIL_COMPONENT} \
"

# Set WEBOS_BLUETOOTH_ENABLED_SERVICE_CLASSES to a space-separated list of
# service classes to be supported by the webOS Bluetooth management service at
# runtime. The assignments are intended to be done in DISTRO.conf and/or
# MACHINE.conf; if none have been done, don't enable support for any service
# classes. Note that including a particular service class might introduce
# additional dependencies into the build image (which is why the setting must be
# global).
WEBOS_BLUETOOTH_ENABLED_SERVICE_CLASSES = "GATT FTP OPP A2DP SPP HFP AVRCP PAN ANCS PBAP MAP"

# Set WEBOS_BLUETOOTH_PAIRING_IO_CAPABILITY in MACHINE.conf to configure the
# Bluetooth pairing IO capability of the adapter. The capability is expected to
# be one of the capabilities mentioned in the Bluetooth specification,
# Bluetooth 4.1 vol 3 Part H "Security Manager Specification" Chapter 2.3.2
# "IO Capabilities". Following are the possible values:
#
#   "DisplayYesNo"
#   "DisplayOnly"
#   "NoInputNoOutput"
#   "KeyboardDisplay"
#   "KeyboardOnly"
# Use "NoInputNoOutput" as its default value (which ends up being used for the
# emulator), which means that the pairing should happen without user intervention.
WEBOS_BLUETOOTH_PAIRING_IO_CAPABILITY ??= "NoInputNoOutput"

WEBOS_VERSION = "1.0.0-31_17db559f23074fc1836f52e3007525bb39fae556"
PR = "r3"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
# VIRTUAL-RUNTIME_bluetooth_stack, WEBOS_BLUETOOTH_PAIRING_IO_CAPABILITY, and
# possibly WEBOS_BLUETOOTH_ENABLED_SERVICE_CLASSES are MACHINE-dependent.
inherit webos_machine_dep
inherit webos_distro_dep
inherit webos_machine_impl_dep

# The convention is for the component "bluetooth-sil-<name>" to build a <name>.so,
# so just pass in <name>.
EXTRA_OECMAKE += "-DWEBOS_BLUETOOTH_SIL:STRING='${BLUETOOTH_SIL_NAME}'"

EXTRA_OECMAKE += "-DWEBOS_BLUETOOTH_ENABLED_SERVICE_CLASSES:STRING='${WEBOS_BLUETOOTH_ENABLED_SERVICE_CLASSES}'"

EXTRA_OECMAKE += "-DWEBOS_BLUETOOTH_PAIRING_IO_CAPABILITY:STRING='${WEBOS_BLUETOOTH_PAIRING_IO_CAPABILITY}'"

PACKAGECONFIG[support-response-bt-prepare-suspend-done] = "-DSUPPORT_RESPONSE_BT_PREPARE_SUSPEND_DONE:BOOL=true,-DSUPPORT_RESPONSE_BT_PREPARE_SUSPEND_DONE:BOOL=false,"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
