# Backported from meta-raspberrypi Yocto 2.7 Warrior
# b48cc66 linux-raspberrypi: add linux-raspberrypi-rt 4.19

LINUX_VERSION ?= "4.19.66"
LINUX_RPI_BRANCH ?= "rpi-4.19.y"

SRCREV = "fc5826fb999e0b32900d1f487e90c27a92010214"

require linux-raspberrypi_4.19.inc
