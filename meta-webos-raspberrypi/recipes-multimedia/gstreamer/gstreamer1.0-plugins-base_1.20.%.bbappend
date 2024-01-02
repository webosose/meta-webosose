# Copyright (c) 2018-2024 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi2"

# gles2 make build error , so override below and remove gles2.
# original: PACKAGECONFIG_GL ?= "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl', '', d)}"
PACKAGECONFIG_GL:rpi = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', ' egl', '', d)}"

WEBOS_REPO_NAME:rpi = "gst-plugins-base"

WEBOS_VERSION:rpi = "1.14.4-3_dba68182527f24b313951383ddbb701ddde340d1"

PACKAGECONFIG[dispmanx] = ",,virtual/libomxil"
OPENGL_WINSYS:append = "${@bb.utils.contains('PACKAGECONFIG', 'dispmanx', ' dispmanx', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:rpi = " \
    file://0001-discoverer-Add-force-sw-decoders-property.patch \
"
