# Copyright (c) 2018-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

# Adds dependency on GPLv2 tbb
PACKAGECONFIG_remove = "tbb"

# Depends on blacklisted glog
PACKAGECONFIG_remove_armv4 = "eigen"
PACKAGECONFIG_remove_armv5 = "eigen"

# http://caprica.lgsvl.com:8080/Errors/Details/1447234
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-apps_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-apps_remove = "bash"
