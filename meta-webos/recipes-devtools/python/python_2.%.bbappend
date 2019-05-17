# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://bpo-35907-cve-2019-9948.patch \
    file://bpo-35907-cve-2019-9948-fix.patch \
    file://bpo-36216-cve-2019-9636.patch \
    file://bpo-36216-cve-2019-9636-fix.patch \
"
