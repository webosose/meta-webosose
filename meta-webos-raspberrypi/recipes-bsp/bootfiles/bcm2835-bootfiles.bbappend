# Backported from meta-raspberrypi Yocto 3.1 Dunfell
# 655dbf3 raspberrypi-firmware: Update to current HEAD
# 7f78604 raspberrypi-firmware: Update to current HEAD
# ae6611e bcm2835-bootfiles: Clarify license conditions
# baba59d linux-raspberrypi=4.19.80 bcm2835-bootfiles=20191021

# Backported from meta-raspberrypi Yocto 3.0 Zeus
# 787bcf3 bcm2835-bootfiles: tighten up dep on rpi-config

RPIFW_DATE = "20191210"
SRCREV = "9d6be5b07e81bdfb9c4b9a560e90fbc7477fdc6e"
SRC_URI[md5sum] = "645e812765c8b4ca05d6cb47a1f67ab0"
SRC_URI[sha256sum] = "484d52caed909fcafbf593cc3e726ea44a9218db7f0aeec843b825797eb9b0fc"

LICENSE = "Broadcom-RPi"
LIC_FILES_CHKSUM = "file://LICENCE.broadcom;md5=c403841ff2837657b2ed8e5bb474ac8d"

do_deploy[depends] += "rpi-config:do_deploy"
