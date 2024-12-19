# Copyright (c) 2017-2024 LG Electronics, Inc.

# The MACHINE.conf for every MACHINE that has an Raspberry Pi BSP must require this
# file.

# All the MACHINE-s supported by this layer are real pieces of hardware.
WEBOS_TARGET_MACHINE_IMPL = "hardware"

MACHINE_FEATURES += "armnn auto-acceleration edgetpu gpu-delegate gl-backend graphics-drm"

# Set gpu memory to "128M"
# Wen use default setting (64M), we got the below error
# [EXIT]: EGL Error : Could not create the egl surface: error = 0x3003
GPU_MEM = "128"

SKIP_RECIPE[omxplayer] ?= "not compatible with webOS Open components"
SKIP_RECIPE[packagegroup-rpi-test] ?= "rdepends on omxplayer"
SKIP_RECIPE[rpi-test-image] ?= "rdepends on packagegroup-rpi-test"

MACHINE_EXTRA_RRECOMMENDS:remove = "kernel-modules"
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS:remove = "kernel-modules"
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS:append = " ${RPI_KERNEL_MODULES}"
RPI_KERNEL_MODULES = " \
    kernel-module-bcm2835-wdt \
    kernel-module-bluetooth \
    kernel-module-brcmfmac \
    kernel-module-bridge \
    kernel-module-btbcm \
    kernel-module-btusb \
    kernel-module-configs \
    kernel-module-evdev \
    kernel-module-hci-uart \
    kernel-module-hid-multitouch \
    kernel-module-hid-sony \
    kernel-module-hmac \
    kernel-module-i2c-bcm2708 \
    kernel-module-media \
    kernel-module-raspberrypi-gpiomem \
    kernel-module-rfcomm \
    kernel-module-snd-bcm2835 \
    kernel-module-snd-usb-audio \
    kernel-module-uinput \
    kernel-module-uvcvideo \
    kernel-module-vc4 \
    kernel-module-videodev \
    kernel-module-xpad \
"
# Few more modules which are automatically loaded on
# raspberrypi4 when all kernel-modules are installed
RPI_KERNEL_MODULES:append:raspberrypi4 = " \
    kernel-module-8021q \
    kernel-module-bcm2835-codec \
    kernel-module-bcm2835-v4l2 \
    kernel-module-bnep \
    kernel-module-cdc-ether \
    kernel-module-cifs \
    kernel-module-cmac \
    kernel-module-fixed \
    kernel-module-joydev \
    kernel-module-libarc4 \
    kernel-module-md4 \
    kernel-module-raspberrypi-hwmon \
    kernel-module-rpivid-mem \
    kernel-module-sch-fq-codel \
    kernel-module-snd-soc-ac108 \
    kernel-module-snd-soc-bcm2835-i2s \
    kernel-module-snd-soc-seeed-voicecard \
    kernel-module-uio-pdrv-genirq \
    kernel-module-v3d \
"

RPI_KERNEL_DEVICETREE_OVERLAYS += " \
    overlays/seeed-4mic-voicecard.dtbo \
    overlays/seeed-8mic-voicecard.dtbo \
"

KERNEL_MODULE_AUTOLOAD:append = " uinput bridge hmac vc4"

# Always enable u-boot
RPI_USE_U_BOOT = "1"

# Always deploy the VFAT partition
SDIMG_VFAT_DEPLOY = "1"

ENABLE_UART = "1"

# Otherwise there are black bars around the screen and touchscreen calibration then doesn't match with the rendered screen in some setups
DISABLE_OVERSCAN = "1"

# Configure all Bluetooth service classes we want to support
WEBOS_BLUETOOTH_ENABLED_SERVICE_CLASSES = "SPP GATT FTP OPP A2DP"

# Use ext4 as a uSD rootfs type and don't build ext3
SDIMG_ROOTFS_TYPE = "ext4"
IMAGE_FSTYPES:remove = "ext3"

# fixed size boot image
WKS_FILE = "sdimage-raspberrypi-webos.wks"

# Use Fake KMS until surface-manager is fixed to support Full KMS, see WRP-5
VC4DTBO = "vc4-fkms-v3d"

# overwrite 5.15 used for rpi3 and rpi4 MACHINEs in kirkstone:
# conf/machine/include/rpi-default-versions.inc:PREFERRED_VERSION_linux-raspberrypi ??= "5.15.%"
# conf/machine/raspberrypi5.conf:PREFERRED_VERSION_linux-raspberrypi = "6.1%"
# all rpi MACHINEs use 6.1 since mickledore and 6.6 since scarthgap (keep in mind that this won't work in langdale)
# See meta-raspberrypi/recipes-kernel/linux/linux-raspberrypi_6.1.bb
# 6.1.61 (backported to kirkstone for rpi5), no 6.1 in langdale, 6.1.54 mickledore, 6.1.61 nanbield, 6.1.77 scarthgap
# and meta-webos-backports/meta-webos-backports-5.0/dynamic-layers/raspberrypi/recipes-kernel/linux/linux-raspberrypi_6.1.bb
# 6.1.77 backported from scarthgap for all releases (the same backports send upstream, except for long dead langdale)
# For scarthgap see meta-raspberrypi/recipes-kernel/linux/linux-raspberrypi_6.6.bb
# 6.6.22 scarthgap (uses this one by default)
# Be aware that meta-webos-backports-5.0 has lower priority (34) than meta-raspberrypi (50) in our weboslayers.py
PREFERRED_VERSION_linux-raspberrypi = "6.1.77%"
