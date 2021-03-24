# Copyright (c) 2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
     file://0001-header-file-shipping-enable-shared-lib.patch \
"

# This will fix Exec plugin coredump
EXTRA_OECMAKE_remove = "-DFLB_TD=1 "

# we need to package unversioned /usr/lib/libfluent-bit.so in ${PN} not ${PN}-dev for service to use fluentbit engine
SOLIBS = ".so*"
FILES_SOLIBSDEV = ""

SYSTEMD_SERVICE_${PN}_remove = "td-agent-bit.service"

INSANE_SKIP_${PN} = "installed-vs-shipped"
