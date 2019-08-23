# Backported from meta-raspberrypi Yocto 2.7 Warrior
# 220c3bf armstubs: Error out when ARMSTUBS is not defined
# 284664b raspberrypi-tools: Update to remove Makefile patch
# 242b1f5 armstubs: Add support for compiling ARM stubs

DESCRIPTION = "Boot strap code that the GPU puts on memory to start running the boot loader"
LICENSE = "Proprietary"

LIC_FILES_CHKSUM = "file://armstub.S;beginline=1;endline=26;md5=9888f34ac06a676129416c952a6a521e"

inherit deploy nopackages

include recipes-bsp/common/raspberrypi-tools.inc

COMPATIBLE_MACHINE = "^rpi$"

S = "${RPITOOLS_S}/armstubs"

export CC8="${CC}"
export LD8="${LD}"
export OBJCOPY8="${OBJCOPY}"
export OBJDUMP8="${OBJDUMP} -maarch64"

do_compile() {
    [ -z "${ARMSTUB}" ] && bbfatal "No ARMSTUB defined for your machine."
    oe_runmake ${ARMSTUB}
}

do_deploy() {
    install -d ${DEPLOYDIR}/${PN}
    cp ${S}/armstub*.bin ${DEPLOYDIR}/${PN}
}

addtask deploy before do_build after do_install
do_deploy[dirs] += "${DEPLOYDIR}/${PN}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
