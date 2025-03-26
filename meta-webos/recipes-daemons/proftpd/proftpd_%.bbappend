# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895307
# ERROR: QA Issue: File /usr/include/proftpd/config.h in package proftpd-dev contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/bin/prxs in package proftpd contains reference to TMPDIR
# File /usr/sbin/proftpd in package proftpd contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
