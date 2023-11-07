# Copyright (c) 2017-2023 LG Electronics, Inc.

SUMMARY = "Fonts used by webOS"
AUTHOR = "Seonmi Jin <seonmi1.jin@lge.com>"
SECTION = "webos/fonts"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=3ef1d10501cf3167830b8f8a8eaa51af \
"

inherit webos_arch_indep

INC_PR = "r1"

RDEPENDS:${PN} = "webos-fontconfig-files"

# Skip the unwanted tasks
do_configure[noexec] = "1"
do_compile[noexec] = "1"

PACKAGES = "${PN}"
FILES:${PN} = "${datadir}/fonts"
