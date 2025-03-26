# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/1072705
# ERROR: QA Issue: File /usr/bin/nana in package nana contains reference to TMPDIR
# File /usr/bin/nana-c++lg in package nana contains reference to TMPDIR
# File /usr/bin/nana-clg in package nana contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
