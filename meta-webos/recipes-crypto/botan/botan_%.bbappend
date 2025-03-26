# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895301
# ERROR: QA Issue: File /usr/bin/botan in package botan-bin contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/include/botan-3/botan/build.h in package botan-dev contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
