# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi3"

PROVIDES_remove = "\
    virtual/libgles2 \
    virtual/egl \
"

RPROVIDES_${PN}_remove ="\
    libgles2 \
    libgl \
    egl \
    libegl \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append = " file://0001-Remove-EGL-dependency.patch"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"

PROVIDES = "virtual/libomxil"
