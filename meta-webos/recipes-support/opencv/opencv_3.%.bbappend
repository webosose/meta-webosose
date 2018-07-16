# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

# Adds dependency on GPLv2 tbb
PACKAGECONFIG_remove = "tbb"

# Depends on blacklisted glog
PACKAGECONFIG_remove_armv4 = "eigen"
PACKAGECONFIG_remove_armv5 = "eigen"
