# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosrpi6"

COMPATIBLE_MACHINE ?= "null"
COMPATIBLE_MACHINE_rpi_aarch64 = "null"
COMPATIBLE_MACHINE_rpi = "(.*)"

# we don't include SRCPV in PV, so we have to manually include SRCREVs in do_fetch vardeps
do_fetch[vardeps] += "SRCREV_ffmpeg"
SRCREV_ffmpeg = "a2d9595a4b4e0e6fe85683ff79774fd618b282cc"
SRCREV_FORMAT = "main_ffmpeg"

SRC_URI += "git://source.ffmpeg.org/ffmpeg;branch=release/3.1;name=ffmpeg;destsuffix=git/ffmpeg"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove = "bash"
