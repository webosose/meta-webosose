# Copyright (c) 2017-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

PACKAGES =+ "${PN}-print-camera-list"
FILES:${PN}-print-camera-list = "${libdir}/*/print-camera-list"

PACKAGES =+ "${PN}-gpl"
FILES:${PN}-gpl = "${libdir}/libgphoto2*/*/pentax.so"

# http://gecko.lge.com:8000/Errors/Details/823101
# libgphoto2-2.5.31/camlibs/ptp2/chdk.c:1187:41: error: passing argument 3 of 'jpeg_mem_dest' from incompatible pointer type [-Wincompatible-pointer-types]
CFLAGS += "-Wno-error=incompatible-pointer-types"
