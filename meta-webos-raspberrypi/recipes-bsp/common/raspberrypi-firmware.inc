# Backported from meta-raspberrypi Yocto 3.1 Dunfell
# 655dbf3 raspberrypi-firmware: Update to current HEAD
# 7f78604 raspberrypi-firmware: Update to current HEAD
# baba59d linux-raspberrypi=4.19.80 bcm2835-bootfiles=20191021
# e811912 raspberrypi-firmware: upgrade to latest to fix vc4-fkms
# 42c02d0 raspberrypi-firmware.inc: Update to 20190718
# ae7aba5 raspberrypi-firmware: Update to 20190709
# 2e45813 firmware: Rename firmware inc file to raspberrypi-firmware.inc

RPIFW_DATE ?= "20191210"
SRCREV ?= "9d6be5b07e81bdfb9c4b9a560e90fbc7477fdc6e"
RPIFW_SRC_URI ?= "https://github.com/raspberrypi/firmware/archive/${SRCREV}.tar.gz"
RPIFW_S ?= "${WORKDIR}/firmware-${SRCREV}"

SRC_URI = "${RPIFW_SRC_URI}"
SRC_URI[md5sum] = "645e812765c8b4ca05d6cb47a1f67ab0"
SRC_URI[sha256sum] = "484d52caed909fcafbf593cc3e726ea44a9218db7f0aeec843b825797eb9b0fc"

PV = "${RPIFW_DATE}"
