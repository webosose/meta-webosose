# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "Media codec interface for webOS"
AUTHOR = "Pankaj Maharana <pankaj.maharana@lge.com>"
SECTION = "webos/media"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519"

inherit webos_component
inherit webos_cmake
inherit webos_system_bus
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_pkgconfig

COMPATIBLE_MACHINE = "^qemux86$|^raspberrypi3$|^raspberrypi3-64$|^raspberrypi4$|^raspberrypi4-64$"

DEPENDS = "boost gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad umediaserver media-resource-calculator"
DEPENDS_append_rpi = " virtual/libomxil"

WEBOS_VERSION = "1.0.0-1_b03f3c5b66cfe17a69337a6be1ffdc6dd6a0b5ce"
PR = "r0"

SRCREV_mcil = "594c42066638b76c917e4f01d96f4ccc3333943a"

WEBOS_REPO_NAME_MCIL ?= "media-codec-ose"
WEBOS_GIT_REPO_MCIL ?= "${WEBOSOSE_GIT_REPO}"
WEBOS_GIT_REPO_COMPLETE_MCIL ?= "${WEBOS_GIT_REPO_MCIL}/${WEBOS_REPO_NAME_MCIL}${WEBOSOSE_GIT_PROTOCOL}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE};name=main"

SRC_URI_append = "\
    ${WEBOS_GIT_REPO_COMPLETE_MCIL};destsuffix=git/src/encoder;name=mcil \
"

S = "${WORKDIR}/git"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so"