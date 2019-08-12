# Copyright (c) 2018-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

do_install_append() {
    install -d ${D}${datadir}/grpc/
    install -m 644 ${S}/etc/roots.pem ${D}${datadir}/grpc/
}
