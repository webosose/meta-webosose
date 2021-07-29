# Copyright (c) 2020 LG Electronics, Inc.

GATOR:raspberrypi4 = " \
    gator \
"

# Gator functionality cannot be verified on webOS rpi64 which cannot boot yet
GATOR:raspberrypi4-64 = ""

RDEPENDS:${PN}:append:raspberrypi4 = "${GATOR}"
