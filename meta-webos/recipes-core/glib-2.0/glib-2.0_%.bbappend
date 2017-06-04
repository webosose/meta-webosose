# Copyright (c) 2013-2017 LG Electronics, Inc.

PKGV .= "-0webos4"
EXTENDPRAUTO_append = "webos4"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://0002-gdbus-codegen-also-replace-character-with-underscore.patch \
"
