# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "webOS OSE developer/test image & SDK"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r0"

require webos-image-devel.bb
inherit webos_image
inherit populate_sdk populate_sdk_qt5

