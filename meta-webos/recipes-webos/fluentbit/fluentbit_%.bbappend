# Copyright (c) 2021-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos6"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

DEPENDS += "openssl"

# fix /usr/include/features.h:397:4: error: #warning _FORTIFY_SOURCE requires compiling with optimization (-O) [-Werror=cpp]
SRC_URI += " \
   file://0001-fix_FORTIFY_SOURCE_optionmization.patch \
"

EXTRA_OECMAKE:append = "-DFLB_SHARED_LIB=ON "
EXTRA_OECMAKE:append = "-DFLB_DEBUG=OFF "

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "fluent-bit.service"
WEBOS_SYSTEMD_SCRIPT = "fluent-bit.sh.in"

do_install:append() {
    # install mk_core*.h
    install -d ${D}${includedir}/monkey
    install -d ${D}${includedir}/monkey/mk_core

    install -v -m 644 ${S}/lib/monkey/include/monkey/mk_core.h ${D}${includedir}/monkey/
    install -v -m 644 ${S}/lib/monkey/include/monkey/mk_core/*.h ${D}${includedir}/monkey/mk_core

    # install libco.h
    install -v -m 644 ${S}/lib/flb_libco/*.h ${D}${includedir}

    # Although enabling FLB_TLS=ON, it can't find flb_tls.h.
    # install fluent-bit/tls/flb_tls.h
    install -d ${D}${includedir}/fluent-bit/tls
    install -v -m 644 ${S}/include/fluent-bit/tls/flb_tls.h ${D}${includedir}/fluent-bit/tls/

    # Although enabling INSTALL_MBEDTLS_HEADER=ON, it can't find mbedtls/*.h.
    # install mbedtls/*.h
    install -d ${D}${includedir}/mbedtls
    install -v -m 644 ${S}/lib/mbedtls-*/include/mbedtls/*.h ${D}${includedir}/mbedtls/

    # install jsmn/jsmn.h
    install -d ${D}${includedir}/jsmn
    install -v -m 644 ${S}/lib/jsmn/jsmn.h ${D}${includedir}/jsmn/
}

# we need to package unversioned /usr/lib/libfluent-bit.so in ${PN} not ${PN}-dev for service to use fluentbit engine
SOLIBS = ".so*"
FILES_SOLIBSDEV = ""

# http://gecko.lge.com:8000/Errors/Details/999806
FILES:${PN} += "${libdir}/fluent-bit/libfluent-bit.so"
