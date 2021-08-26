# Copyright (c) 2017-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos5"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += "\
    file://passwd.master \
    file://group.master \
"

do_configure_prepend () {
    cp -v ${WORKDIR}/passwd.master ${S}/
    cp -v ${WORKDIR}/group.master ${S}/
}
