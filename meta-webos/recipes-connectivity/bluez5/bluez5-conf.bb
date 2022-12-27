# Copyright (c) 2014-2023 LG Electronics, Inc.

SUMMARY = "Machine specific configuration files for bluez5"
AUTHOR = "Simon Busch <simon.busch@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PV = "1.0.0"
PR = "r0"

inherit webos_machine_dep

ALLOW_EMPTY:${PN} = "1"
