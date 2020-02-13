# Copyright (c) 2020 LG Electronics, Inc.

SUMMARY = "Open webOS edition of the open-source glibcurl library"
AUTHOR = "Maksym Shevchenko <myshevchenko@luxoft.com>"
SECTION = "libs"
LICENSE = "MIT & BSD"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COMMON_LICENSE_DIR}/BSD;md5=3775480a712fc46a69647678acb234cb \
"

DEPENDS = "glib-2.0 curl"

WEBOS_VERSION = "0.7.3-5_02973b88e5305769c281f728d9bebfff0c6f0da7"
PR = "r0"

PV = "0.7.3+git${SRCPV}"

inherit webos_upstream_from_repo
inherit webos_public_repo
inherit webos_cmake
inherit webos_library
inherit webos_enhanced_submissions

SRC_URI = "git://github.com/openwebos/glibcurl.git \
           file://glibcurl_patchformmsservice_1.patch \
"

S = "${WORKDIR}/git"
