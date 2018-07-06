# Copyright (c) 2017-2018 LG Electronics, Inc.

# You don't need to change this value when you're changing just a RDEPENDS_${PN} variable.
EXTENDPRAUTO_append_rpi = "webosrpi1"

MEDIA = " \
    libndl-directmedia2 \
    gstreamer1.0 \
    gstreamer1.0-libav \
    gstreamer1.0-omx \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-ugly \
    g-media-pipeline \
"
# Until build issues caused by PLAT-44962 are fixed in PLAT-45700
MEDIA_raspberrypi3-64 = ""

RDEPENDS_${PN}_append_rpi = " \
    com.webos.service.audiooutput \
    ${MEDIA} \
"
