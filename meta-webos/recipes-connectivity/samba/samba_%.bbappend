# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

# This needs newer gnutls version that the 3.3.27 GPLv2 version we have from meta-gplv2
# | Checking for gnutls >= 3.4.7
# : not found
# http://caprica.lgsvl.com:8080/Errors/Details/905992
PACKAGECONFIG_remove = "ad-dc gnutls"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-ctdb-tests_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ctdb-tests_remove = "bash"
