# Copyright (c) 2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
     file://0001-header-file-shipping-enable-shared-lib.patch \
"

do_install_append () {
    # flunet-bit.service will be installed /etc/systemd/system
    rm -f ${D}/lib/systemd/system/fluent-bit.service
    rmdir ${D}/lib/systemd/system
    rmdir ${D}/lib/systemd
    rmdir ${D}/lib

    install -d ${D}${sysconfdir}/systemd/system
    install -m  0644 ${S}/init/fluent-bit.service ${D}${sysconfdir}/systemd/system
}

# This will fix Exec plugin coredump
EXTRA_OECMAKE_remove = "-DFLB_TD=1 "

# SYSTEMD_SERVICE td-agent-bit.service will not be installed
FILES_${PN}_append = "${sysconfdir}/systemd/system/*"
SYSTEMD_SERVICE_${PN} = "fluent-bit.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"

# we need to package unversioned /usr/lib/libfluent-bit.so in ${PN} not ${PN}-dev for service to use fluentbit engine
SOLIBS = ".so*"
FILES_SOLIBSDEV = ""
