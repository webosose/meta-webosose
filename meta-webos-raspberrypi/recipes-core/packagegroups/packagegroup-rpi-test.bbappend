# Copyright (c) 2017-2020 LG Electronics, Inc.

# You don't need to change this value when you're changing just a RDEPENDS:${PN} variable.
EXTENDPRAUTO:append:rpi = "webosrpi3"

RDEPENDS:${PN}:remove:rpi:aarch64 = "omxplayer"

# omxplayer also isn't available for e.g. qemux86
# and components like wiringpi, rpio, rpi-gpio, pi-blaster
# doesn't make much sense to include in non-rpi images
COMPATIBLE_MACHINE = "^rpi$"
