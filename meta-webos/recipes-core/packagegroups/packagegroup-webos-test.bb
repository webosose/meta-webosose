# Copyright (c) 2014-2021 LG Electronics, Inc.

SUMMARY = "Components for testing added to webOS OSE -devel images"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS:${PN} variable.
PR = "r15"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

VIRTUAL-RUNTIME_surface-manager-tests ?= ""
VIRTUAL-RUNTIME_umediaserver-python ?= ""
VIRTUAL-RUNTIME_umediaserver-python:armv4 = ""
VIRTUAL-RUNTIME_umediaserver-python:armv5 = ""

# ptest's default approach to running a component's tests is 'make test'.
# glib-2.0 and dbus certainly assume this, and that's what their run-ptest
# scripts do. But they don't follow https://wiki.yoctoproject.org/wiki/Ptest
# and include an 'RDEPENDS:${PN}-ptest += "make"'. As we know at least two
# components need it, add make to the general dependencies for the packagegroup.
RDEPENDS:${PN} = "\
    db8-tests \
    glib-2.0-utils \
    iotop \
    ltp \
    make \
    ptest-runner \
    sam-tests \
    ${VIRTUAL-RUNTIME_surface-manager-tests} \
    ${VIRTUAL-RUNTIME_umediaserver-python} \
"
