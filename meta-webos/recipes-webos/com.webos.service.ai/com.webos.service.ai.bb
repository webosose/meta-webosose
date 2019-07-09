# Copyright (c) 2018-2020 LG Electronics, Inc.

SUMMARY = "Ai service for voice/face/gesture recognition"
AUTHOR = "Kyungjik Min <dp.min@lge.com>"
SECTION = "webos/extended-service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "glib-2.0 luna-service2 json-c pmloglib libgoogleassistant"

# libgoogleassistant contains prebuilt binaries useful only on raspberrypi3
COMPATIBLE_MACHINE = "^raspberrypi3$|^raspberrypi4$"
COMPATIBLE_MACHINE_raspberrypi3-64 = "^$"
COMPATIBLE_MACHINE_raspberrypi4-64 = "^$"

WEBOS_VERSION = "1.0.0-6_34af70c0f579ecea63d0e430183e0a20c47953d7"
PR = "r2"

inherit systemd
inherit webos_public_repo
inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PNBLACKLIST[com.webos.service.ai] ?= "Depends on libgoogleassistant which needs to be updated for new protobuf"
