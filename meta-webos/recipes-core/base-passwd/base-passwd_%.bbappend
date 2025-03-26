# Copyright (c) 2017-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos8"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += "\
    file://passwd.master \
    file://group.master \
"

do_configure:prepend () {
    cp -v ${UNPACKDIR}/passwd.master ${S}/
    cp -v ${UNPACKDIR}/group.master ${S}/
}
