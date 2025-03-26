# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895296
# ERROR: QA Issue: File /usr/src/debug/python3-pyproj/3.6.1/pyproj/_transformer.c in package python3-pyproj-src contains reference to TMPDIR
# File /usr/src/debug/python3-pyproj/3.6.1/pyproj/_compat.c in package python3-pyproj-src contains reference to TMPDIR
# File /usr/src/debug/python3-pyproj/3.6.1/pyproj/database.c in package python3-pyproj-src contains reference to TMPDIR
# File /usr/src/debug/python3-pyproj/3.6.1/pyproj/_datadir.c in package python3-pyproj-src contains reference to TMPDIR
# File /usr/src/debug/python3-pyproj/3.6.1/pyproj/_crs.c in package python3-pyproj-src contains reference to TMPDIR
# File /usr/src/debug/python3-pyproj/3.6.1/pyproj/_geod.c in package python3-pyproj-src contains reference to TMPDIR
# File /usr/src/debug/python3-pyproj/3.6.1/pyproj/_sync.c in package python3-pyproj-src contains reference to TMPDIR
# File /usr/src/debug/python3-pyproj/3.6.1/pyproj/list.c in package python3-pyproj-src contains reference to TMPDIR
# File /usr/src/debug/python3-pyproj/3.6.1/pyproj/_network.c in package python3-pyproj-src contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/lib/python3.12/site-packages/pyproj/_crs.cpython-312-aarch64-linux-gnu.so in package python3-pyproj contains reference to TMPDIR
# File /usr/lib/python3.12/site-packages/pyproj/_transformer.cpython-312-aarch64-linux-gnu.so in package python3-pyproj contains reference to TMPDIR
# File /usr/lib/python3.12/site-packages/pyproj/_datadir.cpython-312-aarch64-linux-gnu.so in package python3-pyproj contains reference to TMPDIR
# File /usr/lib/python3.12/site-packages/pyproj/_sync.cpython-312-aarch64-linux-gnu.so in package python3-pyproj contains reference to TMPDIR
# File /usr/lib/python3.12/site-packages/pyproj/database.cpython-312-aarch64-linux-gnu.so in package python3-pyproj contains reference to TMPDIR
# File /usr/lib/python3.12/site-packages/pyproj/_network.cpython-312-aarch64-linux-gnu.so in package python3-pyproj contains reference to TMPDIR
# File /usr/lib/python3.12/site-packages/pyproj/_compat.cpython-312-aarch64-linux-gnu.so in package python3-pyproj contains reference to TMPDIR
# File /usr/lib/python3.12/site-packages/pyproj/_geod.cpython-312-aarch64-linux-gnu.so in package python3-pyproj contains reference to TMPDIR
# File /usr/lib/python3.12/site-packages/pyproj/list.cpython-312-aarch64-linux-gnu.so in package python3-pyproj contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
