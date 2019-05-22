# Copyright (c) 2013-2019 LG Electronics, Inc.

EXTENDPRAUTO = "webos1"

PACKAGES =+ "${PN}-gdisk ${PN}-cgdisk ${PN}-fixparts ${PN}-sgdisk"
FILES_${PN}-gdisk += "${sbindir}/gdisk"
FILES_${PN}-cgdisk += "${sbindir}/cgdisk"
FILES_${PN}-sgdisk += "${sbindir}/sgdisk"
FILES_${PN}-fixparts += "${sbindir}/fixparts"
