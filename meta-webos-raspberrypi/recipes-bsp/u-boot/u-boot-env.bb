SUMMARY = "U-boot boot scripts for Raspberry Pi"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
COMPATIBLE_MACHINE = "^rpi$"

DEPENDS = "u-boot-mkimage-native"

PV = "1.0.0"
PR = "r0"

SRC_URI = "file://uboot-env.txt"

do_compile() {
    uboot-mkenvimage -s 16384 -o uboot.env ${WORKDIR}/uboot-env.txt
}

inherit deploy

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 uboot.env ${DEPLOYDIR}
}

addtask do_deploy after do_compile before do_build
