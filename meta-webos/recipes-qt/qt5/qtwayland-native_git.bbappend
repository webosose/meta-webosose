# Copyright (c) 2014-2019 LG Electronics, Inc.

AUTHOR = "Anupam Kaul <anupam.kaul@lge.com>"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtwayland-native:"

WEBOS_VERSION = "5.6.3-4_21bcc61d4a3da2873d9650b19771ffddf31d6bc0"

EXTENDPRAUTO_append = "webos3"

# Upstream 5.5.0 recipe updated LIC_FILES_CHKSUM
LIC_FILES_CHKSUM = " \
    file://LICENSE.LGPLv21;md5=4bfd28363f541b10d9f024181b8df516 \
    file://LICENSE.LGPLv3;md5=e0459b45c5c4840b353141a8bbed91f0 \
    file://LICENSE.GPLv3;md5=88e2b9117e6be406b5ed6ee4ca99a705 \
    file://LGPL_EXCEPTION.txt;md5=9625233da42f9e0ce9d63651a9d97654 \
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
