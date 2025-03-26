# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895300
# ERROR: QA Issue: File /usr/bin/lpstat in package lprng contains reference to TMPDIR
# File /usr/bin/lp in package lprng contains reference to TMPDIR
# File /usr/bin/lpq in package lprng contains reference to TMPDIR
# File /usr/bin/lpr in package lprng contains reference to TMPDIR
# File /usr/bin/cancel in package lprng contains reference to TMPDIR
# File /usr/bin/lprm in package lprng contains reference to TMPDIR
# File /usr/sbin/checkpc in package lprng contains reference to TMPDIR
# File /usr/sbin/lprng_certs in package lprng contains reference to TMPDIR
# File /usr/sbin/lpd in package lprng contains reference to TMPDIR
# File /usr/sbin/lpc in package lprng contains reference to TMPDIR
# File /usr/sbin/lprng_index_certs in package lprng contains reference to TMPDIR
# File /etc/lpd/lpd.conf in package lprng contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
