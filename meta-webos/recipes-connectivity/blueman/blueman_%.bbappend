# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895298
# ERROR: QA Issue: File /usr/src/debug/blueman/2.3.5/module/_blueman.c in package blueman-src contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/lib/python3.12/site-packages/_blueman.so in package blueman contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
