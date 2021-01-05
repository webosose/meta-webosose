# Copyright (c) 2020-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "iot1"

#LICENSE = "Apache-2.0 & MIT"
#LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# Needed only for old Yocto 2.2
FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

WEBOS_VERSION = "3.0.0-56_93963a4f8b91dbaffc412bdf7ec5397bc41df71f"

SRC_URI += " \
    file://0001-remove-default-target-service.patch \
    file://0002-mask-services.patch \
    file://0003-remove-bootd-sub-target-wants.patch \
"
