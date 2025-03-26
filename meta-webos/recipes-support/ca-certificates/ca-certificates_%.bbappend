# Copyright (c) 2013-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos3"

CERT_SOURCE_DIR = "${datadir}/ca-certificates"
CERT_TARGET_DIR = "${sysconfdir}/ssl/certs"

inherit webos_certificates

do_install:append() {
    cd ${D}${sysconfdir}/ssl/certs
    for a in *.pem
    do
        if [ -e $a ] ; then
            cat $a >>${D}${sysconfdir}/ssl/certs/ca-certificates.crt
        fi
    done
}

# ca-certificats is allarch, so it either has to RDEPENDS on ${MLPREFIX}openssl or
# in this case it's easier to just remove the dependency and pull it from somewhere
# else
RDEPENDS:${PN}:remove = "openssl"
