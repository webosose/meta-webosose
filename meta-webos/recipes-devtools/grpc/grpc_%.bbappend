# Copyright (c) 2018-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos5"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-Fixed-crash-issue-of-TTS-Service.patch"

do_install:append() {
    install -d ${D}${datadir}/grpc/
    install -m 644 ${S}/etc/roots.pem ${D}${datadir}/grpc/
}
