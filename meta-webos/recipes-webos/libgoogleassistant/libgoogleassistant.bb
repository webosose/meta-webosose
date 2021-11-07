# Copyright (c) 2018-2021 LG Electronics, Inc.

SUMMARY = "Google assistant engine library"
AUTHOR = "Kyungjik Min <dp.min@lge.com>"
SECTION = "webos/library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=9e4744182d366ff5258e3268c575afe2 \
"

DEPENDS = "snowboy glib-2.0 googleapis grpc json-c pmloglib pulseaudio"
RDEPENDS_${PN}_class-target = "snowboy-models"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_class-target += "${VIRTUAL-RUNTIME_bash}"

WEBOS_VERSION = "1.0.1-9_33a4b718c378e8b54330dece6093c7b0ea716316"
PR = "r7"

inherit webos_library
inherit webos_cmake
inherit webos_machine_impl_dep
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DGOOGLEAPIS_PATH=${STAGING_INCDIR}/google"

INSANE_SKIP_${PN} = "textrel"

# The same restriction as in
# meta-webos/recipes-upstreamable/snowboy/snowboy_%.bbappend
# as this depends on snowboy
COMPATIBLE_MACHINE = "rpi|aarch64|x86-64|qemux86-64"
