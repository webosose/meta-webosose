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
