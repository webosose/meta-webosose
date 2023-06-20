# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi1"

RDEPENDS:${PN}:append:rpi = " \
    kernel-module-v3d \
    mesa-megadriver \
"
