# Copyright (c) 2020 LG Electronics, Inc.

SUMMARY = "NMEA Parser Class Library"
DESCRIPTION = "Parser to support NMEA 0813 standard"
HOMEPAGE = "https://visualgps.github.io/NMEAParser/"
SECTION = "location"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cf0a9a81f2741b7dba6a03714bd18a38"

PR = "r0"

inherit webos_cmake

SRCREV = "d3029a5dc8833d9e76146259a6a4ff9bf225d9e2"
SRC_URI = "git://github.com/VisualGPS/NMEAParser.git;branch=master;protocol=https \
          file://0001-Fix-Compilation-with-webOS.patch \
"
S = "${WORKDIR}/git"
