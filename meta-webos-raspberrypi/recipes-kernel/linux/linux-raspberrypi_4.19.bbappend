# Copyright (c) 2019-2020 LG Electronics, Inc.

# Backported from meta-raspberrypi Yocto 3.1 Dunfell
# 29fd4d4 linux-raspberrypi: bump to Linux version 4.19.93
# a0a5d38 linux-raspberrypi: Bump to 4.19 recipe to 4.19.88
# b0600bf linux-raspberrypi: Bump to 4.19 recipe to 4.19.81
# baba59d linux-raspberrypi=4.19.80 bcm2835-bootfiles=20191021

# Backported from meta-raspberrypi Yocto 3.0 Zeus
# cee2557 linux-raspberrypi: Updating the linux revision to resolve video rendering issue
LINUX_VERSION = "4.19.93"
SRCREV = "3fdcc814c54faaf4715ad0d12371c1eec61bf1dc"


# security.cfg - enable security features. Should be applied to default configuration.
SRC_URI_append = " \
    file://0001-bcm2835-v4l2-codec-fix-vchiq-mmal-renable.patch \
    file://governor.cfg \
    file://ntfs.cfg \
    file://zram.cfg \
    file://security.cfg \
"
