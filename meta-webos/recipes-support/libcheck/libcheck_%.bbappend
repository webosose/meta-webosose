# Copyright (c) 2014-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

# Use awk (from busybox) instead of detecting what's available on the build
# machine.
RDEPENDS_${PN}_append_class-target = " busybox"

AWK = "awk"
export AWK_PATH = "${bindir}/${AWK}"

CACHED_CONFIGUREVARS += "ac_cv_prog_AWK=${AWK}"
CACHED_CONFIGUREVARS += "ac_cv_path_AWK_PATH=${AWK_PATH}"
