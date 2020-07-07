# Copyright (c) 2012-2020 LG Electronics, Inc.

SUMMARY = "Stubbed implementation of the webOS CPU shares scripts"
AUTHOR = "Maksym Shevchenko <myshevchenko@luxoft.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

RPROVIDES_${PN} = "cpushareholder"

WEBOS_VERSION = "2.0.1-2_d740e5161484db8079fc1641737576e087965b85"
PR = "r4"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_arch_indep
inherit webos_cmake
inherit webos_program

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
