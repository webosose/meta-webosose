# Copyright (c) 2015 LG Electronics, Inc.

SUMMARY = "Tinyalsa utilities"
AUTHOR = "Manohar Babu <manohar.babu@lge.com>"
SECTION = "devel/libs"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://NOTICE;md5=e04cd6fa58488e016f7fb648ebea1db4"

PR = "r0"

inherit autotools

SRC_URI = "git://android.googlesource.com/platform/external/tinyalsa;protocol=https \
          file://0001-Add-configure-file-to-build-package.patch"
SRCREV = "825741d2be7b3dd418c515cf4ab676cf6164a6a0"
S = "${WORKDIR}/git"
