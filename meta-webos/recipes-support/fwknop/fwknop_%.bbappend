# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895302
# ERROR: QA Issue: File /usr/bin/fwknop in package fwknop-client contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/lib/libfko.so.3.0.0 in package fwknop contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/sbin/fwknopd in package fwknop-daemon contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
