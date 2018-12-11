# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi4"

PROVIDES_remove_rpi = "\
    virtual/libgles2 \
    virtual/egl \
"

RPROVIDES_${PN}_remove_rpi ="\
    libgles2 \
    libgl \
    egl \
    libegl \
"

FILESEXTRAPATHS_prepend_rpi := "${THISDIR}/${BPN}:"
SRC_URI_append_rpi = " \
    file://0001-openmaxil-add-pkg-config-file.patch \
    file://0002-Remove-EGL-dependency.patch \
"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target_rpi = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_rpi = "bash"

PROVIDES_append_rpi = " virtual/libomxil"
