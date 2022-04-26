# Copyright (c) 2018-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

# Adds dependency on GPLv2 tbb
PACKAGECONFIG:remove = "tbb"

# Depends on blacklisted glog
PACKAGECONFIG:remove:armv4 = "eigen"
PACKAGECONFIG:remove:armv5 = "eigen"

# http://caprica.lgsvl.com:8080/Errors/Details/1447234
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-apps:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-apps:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
