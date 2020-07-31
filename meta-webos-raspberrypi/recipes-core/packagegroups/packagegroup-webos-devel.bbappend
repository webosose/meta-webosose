# Copyright (c) 2020 LG Electronics, Inc.

GATOR_raspberrypi4 = " \
    gator \
"

# Gator functionality cannot be verified on webOS rpi64 which cannot boot yet
GATOR_raspberrypi4-64 = ""

RDEPENDS_${PN}_append_raspberrypi4 = "${GATOR}"
