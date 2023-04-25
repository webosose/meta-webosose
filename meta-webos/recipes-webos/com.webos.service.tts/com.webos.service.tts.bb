# Copyright (c) 2018-2023 LG Electronics, Inc.

SUMMARY = "webOS text to speech service"
SECTION = "webos/base"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
    file://oss-pkg-info.yaml;md5=eb0fefa4904ac8820261e985096d5ad4 \
"

DEPENDS = "glib-2.0 luna-service2 libpbnjson pmloglib json-c pulseaudio googleapis grpc"

WEBOS_VERSION = "1.0.0-26_084a9644cccba33baec3335ad89174a4969ebd75"
PR = "r8"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DGOOGLEAPIS_PATH=${STAGING_INCDIR}/google"

FILES:${PN} += "${webos_sysbus_datadir}"
