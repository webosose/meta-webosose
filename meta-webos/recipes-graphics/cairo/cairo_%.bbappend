# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Set USE_X11/EGL_NO_X11 explicitly for using some eglplatform header.
# http://gecko.lge.com:8000/Errors/Details/750360
TARGET_CFLAGS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '-DUSE_X11', '-DEGL_NO_X11', d)}"
