# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895299
# ERROR: QA Issue: File /usr/bin/unshar in package sharutils contains reference to TMPDIR
# File /usr/bin/uudecode.sharutils in package sharutils contains reference to TMPDIR
# File /usr/bin/shar in package sharutils contains reference to TMPDIR
# File /usr/bin/uuencode.sharutils in package sharutils contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
