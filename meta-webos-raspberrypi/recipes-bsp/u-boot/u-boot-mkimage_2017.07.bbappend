# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi1"

do_install_append () {
    install -m 0755 tools/mkenvimage ${D}${bindir}/uboot-mkenvimage
    ln -sf uboot-mkenvimage ${D}${bindir}/mkenvimage
}
