LINUX_VERSION ?= "4.14.39"

require recipes-kernel/linux/linux-raspberrypi.inc

SRCREV = "865ddc1393f558198e7e7ce70928ff2e49c4f7f6"
SRC_URI = "git://github.com/raspberrypi/linux.git;branch=rpi-4.14.y"
