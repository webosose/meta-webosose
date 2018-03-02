# Copyright (c) 2014 LG Electronics, Inc.
#
# webos_pmlog_config

# This class is to be inherited by pmloglib and pmlogdaemon recipes
#

EXTRA_OECMAKE += "-DENABLE_LOGGING:BOOL=YES"
