# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi2"

# omxplayer also isn't available for e.g. qemux86
# and components like wiringpi, rpio, rpi-gpio, pi-blaster
# doesn't make much sense to include in non-rpi images
COMPATIBLE_MACHINE = "^rpi$"

