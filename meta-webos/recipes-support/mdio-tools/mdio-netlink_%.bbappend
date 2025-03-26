# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/1072764
# ERROR: QA Issue: File /usr/lib/modules/5.15.167-b0-saturn/extra/mdio-netlink.ko in package kernel-module-mdio-netlink-5.15.167-b0-saturn contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
