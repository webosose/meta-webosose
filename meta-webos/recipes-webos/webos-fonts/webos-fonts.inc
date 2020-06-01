# Copyright (c) 2017-2020 LG Electronics, Inc.

SUMMARY = "Fonts used by webOS"
SECTION = "webos/fonts"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=7ae5bb88c7d884fe63c019118794b221 \
"

inherit webos_arch_indep

# Skip the unwanted tasks
do_configure[noexec] = "1"
do_compile[noexec] = "1"

PACKAGES = "${PN}"
FILES_${PN} = "${datadir}/fonts"
