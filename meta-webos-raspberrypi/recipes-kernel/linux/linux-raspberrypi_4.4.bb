# Copyright (c) 2019 LG Electronics, Inc.

# Bringup linux-raspberrypi-4.4 from meta-raspberrypi morty(2a19226)

LINUX_VERSION ?= "4.4.50"

SRCREV = "04c8e47067d4873c584395e5cb260b4f170a99ea"
SRC_URI = "git://github.com/raspberrypi/linux.git;protocol=git;branch=rpi-4.4.y \
           file://0001-fix-dtbo-rules.patch \
"

# linux-raspberrypi.inc in meta-raspberrypi layer, so we should modify the path of it.
# require linux-raspberrypi.inc
require recipes-kernel/linux/linux-raspberrypi.inc

# Backport some kernel patches for fixing compile errors from rpi-4.14.y
# Refer https://patchwork.ozlabs.org/patch/909905/ https://patchwork.kernel.org/patch/10545399/
SRC_URI += " \
    file://0001-log2-make-order_base_2-behave-correctly-on-const-inp.patch \
    file://0002-give-up-on-gcc-ilog2-constant-optimizations.patch \
    file://0003-stable-4.4-ARM-fix-put_user-for-gcc-8.patch \
"
