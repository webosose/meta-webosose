# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos4"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += "\
    file://passwd.master \
    file://group.master \
"

do_configure_prepend () {
    cp -v ${WORKDIR}/passwd.master ${S}/
    cp -v ${WORKDIR}/group.master ${S}/
}
