# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

PACKAGECONFIG[sdl2] = "--with-sdlabi=2.0,--with-sdlabi=1.2,libsdl2"
PACKAGECONFIG[virglrenderer] = "--enable-virglrenderer,--disable-virglrenderer,virglrenderer"

PACKAGECONFIG_class-native = "fdt alsa sdl sdl2 virglrenderer glx"
PACKAGECONFIG_class-nativesdk = "fdt sdl sdl sdl2 virglrenderer"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://opengl-without-x11.patch"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove = "bash"
