# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# We need ncurses-terminfo-base even NO_RECOMMENDATIONS is set
RDEPENDS:${PN}-libtinfo = "${PN}-terminfo-base"
