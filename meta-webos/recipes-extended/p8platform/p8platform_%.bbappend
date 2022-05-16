# Copyright (c) 2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# To avoid below errors when populate_sdk
# Collected errors:
# * Solver encountered 1 problem(s):
# * Problem 1/1:
# *   - nothing provides p8platform = 2.1.0.1-r0 needed by p8platform-dev-2.1.0.1-r0.raspberrypi4_64
RDEPENDS:${PN}-dev = ""
