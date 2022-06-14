# Copyright (c) 2018-2020 LG Electronics, Inc.

# workaround for the upstream branch name change from master to main.
# inherit line below can be removed when the request is applied upstream.
# : https://lists.openembedded.org/g/openembedded-devel/message/97511
inherit fix_branch_name

EXTENDPRAUTO:append = "webos4"

do_install:append() {
    install -d ${D}${datadir}/grpc/
    install -m 644 ${S}/etc/roots.pem ${D}${datadir}/grpc/
}
