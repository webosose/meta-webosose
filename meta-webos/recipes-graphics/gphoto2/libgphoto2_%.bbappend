# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"

PACKAGES =+ "${PN}-print-camera-list"
FILES_${PN}-print-camera-list = "${libdir}/*/print-camera-list"

PACKAGES =+ "${PN}-gpl"
FILES_${PN}-gpl = "${libdir}/libgphoto2*/*/pentax.so"
