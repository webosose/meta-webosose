# Copyright (c) 2013-2019 LG Electronics, Inc.

# A blacklisted recipe is ignored by bitbake. The reason it is useful to be able
# to have bitbake ignore a recipe is because, although PREFERRED_PROVIDER can be
# used to select which recipe is used to fulfill a build-time dependency, bitbake
# does not have a PREFERRED_RPROVIDER to allow specification of which package
# should be used to satisfy a run-time dependency. When two recipes both provide
# common packages, but one recipe provides additional packages that the other
# doesn't, then the only way to guarantee that the first recipe will never be
# used is to blacklist it.

PNBLACKLIST[vboxguestdrivers] ?= "unused in webOS world"

# Use webOS qt5 configuration
PNBLACKLIST[qwt-qt5] ?= "ERROR: Unknown module(s) in QT: printsupport opengl http://caprica.lgsvl.com:8080/Errors/Details/1509145"
PNBLACKLIST[libconnman-qt5] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509142"
PNBLACKLIST[libqofono] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509141"
PNBLACKLIST[qtwebkit] ?= "Could not find a package configuration file provided by Qt5Widgets, because widgets are disabled in our builds http://caprica.lgsvl.com:8080/Errors/Details/1509126"
PNBLACKLIST[maliit-framework-qt5] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509125"
PNBLACKLIST[maliit-plugins-qt5] ?= "depends on blacklisted maliit-framework-qt5"
PNBLACKLIST[qt5-creator] ?= "depends on blacklisted qtwebkit"

PNBLACKLIST[kernel-devsrc] ?= "BROKEN for aarch64: make[4]: *** No rule to make target `kernel-devsrc/1.0-r0/image/usr/src/kernel/arch/arm64/boot/dts/broadcom/../overlays/Makefile'.  Stop http://caprica.lgsvl.com:8080/Errors/Details/1509158"
PNBLACKLIST[glmark2] ?= "broken: we don't build libGL with mesa: cannot find -lGL http://caprica.lgsvl.com:8080/Errors/Details/1509155"
PNBLACKLIST[sysstat] ?= "BROKEN: install: invalid group ‘man’ http://caprica.lgsvl.com:8080/Errors/Details/1509136"
PNBLACKLIST[man-db] ?= "BROKEN: without --disable-cache-owner: chown: invalid user: ?man:man? our passwd doesn't have man user and since http://git.openembedded.org/openembedded-core/commit/?id=7080df4b6bc50440eed600e81f2c6fa76a80623d it's needed http://caprica.lgsvl.com:8080/Errors/Details/1509154"
PNBLACKLIST[hplip] ?= "BROKEN: configure: error: could not force soft-float and chgrp: invalid group: ‘lp’ http://caprica.lgsvl.com:8080/Errors/Details/1509153"
PNBLACKLIST[gst-plugins-gl] ?= "GL/glew.h:1205:14: fatal error: GL/glu.h: No such file or directory http://caprica.lgsvl.com:8080/Errors/Details/1509152"
PNBLACKLIST[poppler] ?= "fails to generate GIR http://caprica.lgsvl.com:8080/Errors/Details/1509140"
PNBLACKLIST[s-suite] ?= "rdepends on blacklisted sysstat"
PNBLACKLIST[mongodb] ?= "src/mongo/util/net/ssl_manager.cpp:557:70: error: invalid conversion from 'size_t*' {aka 'unsigned int*'} to 'long unsigned int*' [-fpermissive] http://caprica.lgsvl.com:8080/Errors/Details/1509767"

# ERROR: QA Issue: ELF binary 'TOPDIR/BUILD/work/qemux86-webos-linux/x264/r2854+gitAUTOINC+e9a5903edf-r0/packages-split/x264/usr/lib/libx264.so.152' has relocations in .text [textrel] http://caprica.lgsvl.com:8080/Errors/Details/1509766
INSANE_SKIP_${PN}_pn-x264 = "textrel"

# QA Issue: ELF binary 'work/raspberrypi3-webos-linux-gnueabi/mpeg2dec/0.5.1-r0/packages-split/libmpeg2/usr/lib/libmpeg2.so.0.1.0' has relocations in .text
INSANE_SKIP_libmpeg2_pn-mpeg2dec += "textrel"

# from meta-ros:
# libxi was skipped: missing required distro feature 'x11' (not in DISTRO_FEATURES)
# libglu was skipped: missing required distro feature 'x11' (not in DISTRO_FEATURES)
# libxmu was skipped: missing required distro feature 'x11' (not in DISTRO_FEATURES)
PNBLACKLIST[libfreenect] ?= "depends on blacklisted libxi, freeglut, libxmu"
PNBLACKLIST[freeglut] ?= "depends on blacklisted libxi, libglu"
# gtk+ was skipped: one of '['directfb', 'x11']' needs to be in DISTRO_FEATURES
# libxinerama was skipped: missing required distro feature 'x11' (not in DISTRO_FEATURES)
PNBLACKLIST[wxwidgets] ?= "depends on blacklisted libxinerama, libglu, gtk, webkit-gtk"

PNBLACKLIST[wxpython] ?= "depends on blacklisted wxwidgets"
PNBLACKLIST[freenect-camera] ?= "depends on blacklisted libfreenect"
PNBLACKLIST[freenect-launch] ?= "rdepends on blacklisted freenect-camera"
PNBLACKLIST[realsense-camera] ?= "depends on librealsenese which is provided by meta-intel-realsense layer which we don't use, see https://github.com/bmwcarit/meta-ros/commit/7f889a8a53fa3febb7e5c51f6f408ddec83bbf26"
PNBLACKLIST[joint-state-publisher] ?= "depends on blacklisted wxpython"

# gst-plugins-ugly was skipped: because it has a restricted license not whitelisted in LICENSE_FLAGS_WHITELIST
PNBLACKLIST[sound-play] ?= "rdepends on blacklisted gst-plugins-ugly"

PNBLACKLIST[geographiclib] ?= "Broken, many unpackaged files http://caprica.lgsvl.com:8080/Errors/Details/1509150"
PNBLACKLIST[python-wstool] ?= "Broken, many unpackaged files http://caprica.lgsvl.com:8080/Errors/Details/1509151"
PNBLACKLIST[speech-tools] ?= "fails to fetch http://caprica.lgsvl.com:8080/Errors/Details/1509132"
PNBLACKLIST[festival] ?= "fails to fetch http://caprica.lgsvl.com:8080/Errors/Details/1509157"
PNBLACKLIST[image-view] ?= "missing gtk dependency http://caprica.lgsvl.com:8080/Errors/Details/1509137"
PNBLACKLIST[velodyne-pointcloud] ?= "fails to fetch http://caprica.lgsvl.com:8080/Errors/Details/1509135"
PNBLACKLIST[velodyne-driver] ?= "fails to fetch http://caprica.lgsvl.com:8080/Errors/Details/1509124"
PNBLACKLIST[velodyne-msgs] ?= "fails to fetch http://caprica.lgsvl.com:8080/Errors/Details/1509123"
PNBLACKLIST[ar-track-alvar] ?= "ar-track-alvar/0.6.1-r0/ar_track_alvar-0.6.1/include/ar_track_alvar/Draw.h:70:86: error: there are no arguments to 'CV_RGB' that depend on a template parameter, so a declaration of 'CV_RGB' must be available [-fpermissive] http://caprica.lgsvl.com:8080/Errors/Details/1509130"
PNBLACKLIST[yocs-localization-manager] ?= "rdepends on blacklisted ar-track-alvar"
PNBLACKLIST[python-orocos-kdl] ?= "depends on sip-native unavailable in our builds http://caprica.lgsvl.com:8080/Errors/Details/1509128"
PNBLACKLIST[kobuki-testsuite] ?= "depends on blacklisted python-orocos-kdl"
PNBLACKLIST[kdl-parser-py] ?= "depends on blacklisted python-orocos-kdl"

# NOTE: Multiple providers are available for runtime python-bson (python-bson, python-pymongo)
PNBLACKLIST[python-bson] ?= "Conflicts with python-pymongo"
