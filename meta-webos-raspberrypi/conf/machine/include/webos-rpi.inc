# Copyright (c) 2017-2018 LG Electronics, Inc.

# The MACHINE.conf for every MACHINE that has an Raspberry Pi BSP must require this
# file.

# All the MACHINE-s supported by this layer are real pieces of hardware.
WEBOS_TARGET_MACHINE_IMPL = "hardware"

MACHINE_FEATURES_append = " vc4graphics"

PREFERRED_PROVIDER_aval-impl = "avoutput-adaptation-layer-rpi"
VIRTUAL-RUNTIME_aval-impl = "avoutput-adaptation-layer-rpi"

VIRTUAL-RUNTIME_alsa-state = "avoutput-adaptation-layer-rpi"
PNBLACKLIST[avoutput-adaptation-layer-mock] ?= "avoutput-adaptation-layer-rpi is preferred"

PNBLACKLIST[omxplayer] ?= "not compatible with webOS Open components"
PNBLACKLIST[packagegroup-rpi-test] ?= "rdepends on omxplayer"
PNBLACKLIST[rpi-test-image] ?= "rdepends on packagegroup-rpi-test"

MACHINE_EXTRA_RRECOMMENDS_remove = "kernel-modules"
MACHINE_EXTRA_RRECOMMENDS_append_raspberrypi3 = " \
    kernel-module-bcm2835-gpiomem \
    kernel-module-i2c-bcm2708 \
    kernel-module-snd-bcm2835 \
    kernel-module-brcmfmac \
    kernel-module-evdev \
    kernel-module-bcm2835-gpiomem \
    kernel-module-bcm2835-wdt \
    kernel-module-uinput \
"
