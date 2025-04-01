# Copyright (c) 2018-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

# opengl causes undeclared dependency on libgudev
# ERROR: gstreamer1.0-plugins-base-1.14.0-r0 do_package_qa: QA Issue: libgstgl-1.0 rdepends on libgudev, but it isn't a build dependency, missing libgudev in DEPENDS or PACKAGECONFIG? [build-deps]
# ERROR: gstreamer1.0-plugins-base-1.14.0-r0 do_package_qa: QA Issue: gstreamer1.0-plugins-base-opengl rdepends on libgudev, but it isn't a build dependency, missing libgudev in DEPENDS or PACKAGECONFIG? [build-deps]
# This problem doesn't exist with newer Yocto with RSS
DEPENDS += "libgudev"

# Set USE_X11/EGL_NO_X11 explicitly for using some eglplatform header.
# http://gecko.lge.com:8000/Errors/Details/750529
TARGET_CFLAGS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'x11', '-DUSE_X11', '-DEGL_NO_X11', d)}"
