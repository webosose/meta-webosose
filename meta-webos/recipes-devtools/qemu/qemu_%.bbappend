# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

PACKAGECONFIG[sdl2] = "--with-sdlabi=2.0,--with-sdlabi=1.2,libsdl2"
PACKAGECONFIG[virglrenderer] = "--enable-virglrenderer,--disable-virglrenderer,virglrenderer"

PACKAGECONFIG_class-native = "fdt alsa sdl sdl2 virglrenderer"
PACKAGECONFIG_class-nativesdk = "fdt sdl sdl sdl2 virglrenderer"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://opengl-without-x11.patch"
