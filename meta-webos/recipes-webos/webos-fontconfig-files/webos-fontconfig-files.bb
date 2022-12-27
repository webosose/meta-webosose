# Copyright (c) 2017-2023 LG Electronics, Inc.

SUMMARY = "Private configuration files for fontconfig"
AUTHOR = "Seonmi Jin <seonmi1.jin@lge.com>"
SECTION = "webos/fonts"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=704f5c65b7aa0484b9e3f01c09e74b58 \
"

WEBOS_VERSION = "1.0.0-5_e3b8d5297c20edd8fc73ee3ac8729094159942ec"
PR = "r4"

inherit webos_enhanced_submissions
inherit fontcache
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# Skip the unwanted tasks
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${datadir}/fontconfig/conf.avail
    install -v -m 644 ${S}/31-webos-aliases.conf ${D}${datadir}/fontconfig/conf.avail
    install -d ${D}${sysconfdir}/fonts/conf.d
    ln -vsf /usr/share/fontconfig/conf.avail/31-webos-aliases.conf ${D}${sysconfdir}/fonts/conf.d/31-webos-aliases.conf
}

FILES:${PN} += "${sysconfdir}/fonts/conf.d/ ${datadir}/fontconfig/"
