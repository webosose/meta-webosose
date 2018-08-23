# Copyright (c) 2017-2018 LG Electronics, Inc.

# The MACHINE.conf for every MACHINE that has an Raspberry Pi BSP must require this
# file.

# All the MACHINE-s supported by this layer are real pieces of hardware.
WEBOS_TARGET_MACHINE_IMPL = "hardware"

# Set gpu memory to "128M"
# Wen use default setting (64M), we got the below error
# [EXIT]: EGL Error : Could not create the egl surface: error = 0x3003
GPU_MEM = "128"

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
    kernel-module-bcm2835-wdt \
    kernel-module-bluetooth \
    kernel-module-brcmfmac \
    kernel-module-btbcm \
    kernel-module-evdev \
    kernel-module-hci-uart \
    kernel-module-i2c-bcm2708 \
    kernel-module-media \
    kernel-module-rfcomm \
    kernel-module-snd-bcm2835 \
    kernel-module-snd-usb-audio \
    kernel-module-uinput \
    kernel-module-uvcvideo \
    kernel-module-videodev \
    kernel-module-xpad \
"

KERNEL_MODULE_AUTOLOAD_append = " uinput xpad"

# Disabling U-Boot until IP address conflict and ALSA issues resolved (PLAT-46515)
# RPI_USE_U_BOOT = "1"
# Enable only for raspberrypi3-64
RPI_USE_U_BOOT_raspberrypi3-64 = "1"

# Always deploy the VFAT partition
SDIMG_VFAT_DEPLOY = "1"

ENABLE_UART = "1"
