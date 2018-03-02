# Copyright (c) 2014-2018 LG Electronics, Inc.

AUTHOR = "Anupam Kaul <anupam.kaul@lge.com>"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtwayland-native:"

WEBOS_VERSION = "5.4.2-1_ee4a49f2ab723631174d7f2b8dae2263565c9ece"

EXTENDPRAUTO_append = "webos2"

# Upstream 5.5.0 recipe updated LIC_FILES_CHKSUM
LIC_FILES_CHKSUM = " \
    file://LICENSE.LGPL;md5=4193e7f1d47a858f6b7c0f1ee66161de \
    file://LICENSE.GPL;md5=d32239bcb673463ab874e80d47fae504 \
    file://LGPL_EXCEPTION.txt;md5=0145c4d1b6f96a661c2c139dfb268fb6 \
    file://LICENSE.FDL;md5=6d9f2a9af4c8b8c3c769f6cc1b6aaf7e \
"

inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

# Carrying the "0001-Install-the-qtwaylandscanner-tool-to-the-native-side" from the
# base recipe
SRC_URI_append = " \
    file://0001-Install-the-qtwaylandscanner-tool-to-the-native-side.patch \
"
