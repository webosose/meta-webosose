# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-kernel-install_remove_class-target = "bash"
RDEPENDS_${PN}-kernel-install_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "bash"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"

RDEPENDS_${PN}_remove = "update-rc.d"

PACKAGECONFIG_remove = " \
    networkd    \
    resolved    \
"
