# Copyright (c) 2024-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Reported in https://lists.openembedded.org/g/openembedded-devel/message/102887"
# textrel since upgrade to go-1.20.4, http://gecko.lge.com:8000/Errors/Details/620828
INSANE_SKIP:${PN} += "textrel"
