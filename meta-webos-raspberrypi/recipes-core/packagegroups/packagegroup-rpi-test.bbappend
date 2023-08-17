# Copyright (c) 2017-2023 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi3"

RDEPENDS:${PN}:remove:rpi:aarch64 = "omxplayer"

# omxplayer also isn't available for e.g. qemux86
# and components like wiringpi, rpio, rpi-gpio, pi-blaster
# doesn't make much sense to include in non-rpi images
COMPATIBLE_MACHINE = "^rpi$"
