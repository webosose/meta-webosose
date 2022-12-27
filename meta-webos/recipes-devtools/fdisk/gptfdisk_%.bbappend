# Copyright (c) 2013-2023 LG Electronics, Inc.

EXTENDPRAUTO = "webos2"

PACKAGES =+ "${PN}-gdisk ${PN}-cgdisk ${PN}-fixparts ${PN}-sgdisk"
FILES:${PN}-gdisk += "${sbindir}/gdisk"
FILES:${PN}-cgdisk += "${sbindir}/cgdisk"
FILES:${PN}-sgdisk += "${sbindir}/sgdisk"
FILES:${PN}-fixparts += "${sbindir}/fixparts"

ALLOW_EMPTY:${PN} = "1"
