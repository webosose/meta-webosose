# Copyright (c) 2018-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

do_install:append() {
    install -d ${D}${datadir}/grpc/
    install -m 644 ${S}/etc/roots.pem ${D}${datadir}/grpc/
}
