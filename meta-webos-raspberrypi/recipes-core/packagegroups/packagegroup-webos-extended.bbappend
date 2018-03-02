# Copyright (c) 2017-2018 LG Electronics, Inc.

# You don't need to change this value when you're changing just a RDEPENDS_${PN} variable.
EXTENDPRAUTO_append_rpi = "webosrpi1"

MEDIA = " \
    libndl-directmedia2 \
"
# Until build issues caused by PLAT-44962 are fixed in PLAT-45700
MEDIA_raspberrypi3-64 = ""

RDEPENDS_${PN}_append_rpi = " \
    ${MEDIA} \
"
