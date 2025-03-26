# Copyright (c) 2024-2025 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi1"

# Restore parts of
# https://github.com/agherzan/meta-raspberrypi/commit/a871ecfb38bfd0763305f7b88437772fc5d60157
# to fix broken symlink brcmfmac43455-sdio.raspberrypi,4-model-b.bin -> ../cypress/cyfmac43455-sdio.bin
# as current linux-firmware-rpidistro provides only
# ../cypress/cyfmac43455-sdio-standard.bin
# ../cypress/cyfmac43455-sdio-minimal.bin
# while brcmfmac43455-sdio.bin symlink adjusted by
# meta-raspberrypi/recipes-kernel/linux-firmware-rpidistro/linux-firmware-rpidistro/0001-Default-43455-firmware-to-standard-variant.patch
# points to existing /cyfmac43455-sdio-standard.bin
# brcmfmac43455-sdio.bin -> ../cypress/cyfmac43455-sdio-standard.bin
do_install:append() {
    ln -svnf brcmfmac43455-sdio.bin ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43455-sdio.raspberrypi,3-model-a-plus.bin
    ln -svnf brcmfmac43455-sdio.bin ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43455-sdio.raspberrypi,3-model-b-plus.bin
    ln -svnf brcmfmac43455-sdio.bin ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43455-sdio.raspberrypi,4-compute-module.bin
    ln -svnf brcmfmac43455-sdio.bin ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43455-sdio.raspberrypi,4-model-b.bin
    ln -svnf brcmfmac43455-sdio.bin ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43455-sdio.raspberrypi,5-model-b.bin
}
