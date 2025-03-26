# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/899672
# ERROR: lib32-gpm-1.99.7+gite82d1a653ca94aa4ed12441424da6ce780b1e530-r0 do_package_qa: QA Issue: File /usr/src/debug/lib32-gpm/1.99.7+gite82d1a653ca94aa4ed12441424da6ce780b1e530/src/prog/gpm-root.c in package lib32-gpm-src contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
