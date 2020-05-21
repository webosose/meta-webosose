# Copyright (c) 2017-2020 LG Electronics, Inc.

# You don't need to change this value when you're changing just a RDEPENDS_${PN} variable.
EXTENDPRAUTO_append_rpi = "webosrpi3"

RDEPENDS_${PN}_remove_rpi_aarch64 = "omxplayer"

# omxplayer also isn't available for e.g. qemux86
# and components like wiringpi, rpio, rpi-gpio, pi-blaster
# doesn't make much sense to include in non-rpi images
COMPATIBLE_MACHINE = "^rpi$"

# It's no longer available, see
# http://wiringpi.com/wiringpi-deprecated/
# and removed already in meta-raspberrypi/zeus in:
# commit e050601d868df6f7b38812ca930e30a6a4cff135
# Author: Khem Raj <raj.khem@gmail.com>
# Date:   Thu Oct 10 07:51:33 2019 -0700
#
#     wiringpi: Delete
RDEPENDS_${PN}_remove = "wiringpi"
