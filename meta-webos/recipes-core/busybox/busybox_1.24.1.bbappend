# Copyright (c) 2012-2017 LG Electronics, Inc.

PKGV .= "-0webos3"
EXTENDPRAUTO_append = "webos10"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://add_tzset-webos.patch"
SRC_URI += "file://add_option_dash_S-webos.patch"
SRC_URI += "file://add_option_dash_U-webos.patch"
SRC_URI += "file://check_for_null_before_passing_to_fputs-webos.patch"

SRC_URI += "file://defconfig"

RPROVIDES_${PN} += "stat"
RPROVIDES_${PN} += "bash /bin/bash"
