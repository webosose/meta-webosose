# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "Storage Access Framework for OSE"
AUTHOR = "RajeshGopu IV <rajeshgopu.iv@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=13b5f44cefd7b1b0040a056eeddf6174"

DEPENDS= "glib-2.0 luna-service2 pmloglib libpbnjson curl"

COMPATIBLE_MACHINE = "^raspberrypi4$|^qemux86$"
COMPATIBLE_MACHINE_raspberrypi4-64 = "^$"

WEBOS_VERSION = "1.0.0-6_7c780c1c9f19692ffb6b9401b7688cc166e8a119"
PR = "r1"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_machine_dep
inherit webos_distro_dep


SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
