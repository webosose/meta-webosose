# Copyright (c) 2014-2024 LG Electronics, Inc.

SUMMARY = "Components for testing added to webOS OSE -devel images"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r16"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

VIRTUAL-RUNTIME_surface-manager-tests ?= ""
VIRTUAL-RUNTIME_umediaserver-python ?= ""
VIRTUAL-RUNTIME_umediaserver-python:armv4 = ""
VIRTUAL-RUNTIME_umediaserver-python:armv5 = ""

RDEPENDS:${PN} = "\
    db8-tests \
    glib-2.0-utils \
    iotop \
    ptest-runner \
    sam-tests \
    ${VIRTUAL-RUNTIME_surface-manager-tests} \
    ${VIRTUAL-RUNTIME_umediaserver-python} \
"
