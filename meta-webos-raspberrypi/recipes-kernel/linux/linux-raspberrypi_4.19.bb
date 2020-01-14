# Backported from meta-raspberrypi Yocto 3.1 Dunfell
# 29fd4d4 linux-raspberrypi: bump to Linux version 4.19.93
# a0a5d38 linux-raspberrypi: Bump to 4.19 recipe to 4.19.88
# b0600bf linux-raspberrypi: Bump to 4.19 recipe to 4.19.81
# baba59d linux-raspberrypi=4.19.80 bcm2835-bootfiles=20191021
# cee2557 linux-raspberrypi: Updating the linux revision to resolve video rendering issue
# 866ccc8 linux-raspberrypi: Update 4.19 recipe to 4.19.71
# 2634621 linux-raspberrypi: add linux-raspberrypi-rt 4.19

LINUX_VERSION ?= "4.19.93"
LINUX_RPI_BRANCH ?= "rpi-4.19.y"

SRCREV = "3fdcc814c54faaf4715ad0d12371c1eec61bf1dc"

require linux-raspberrypi_4.19.inc
