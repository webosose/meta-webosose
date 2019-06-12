# Copyright (c) 2018-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-Don-t-segfault-when-product-name-from-BIOS-is-empty.patch"

do_install_append() {
    install -d ${D}${datadir}/grpc/
    install -m 644 ${S}/etc/roots.pem ${D}${datadir}/grpc/
}
