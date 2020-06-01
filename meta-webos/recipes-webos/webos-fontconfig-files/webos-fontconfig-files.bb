# Copyright (c) 2017-2020 LG Electronics, Inc.

SUMMARY = "Private configuration files for fontconfig"
AUTHOR = "Seonmi Jin <seonmi1.jin@lge.com>"
SECTION = "webos/fonts"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=c090d72d2a92f8ce7fe6ebd27e93176e \
"

WEBOS_VERSION = "1.0.0-4_0b84b8467b4733a1db299443dfa997c54e934a2c"
PR = "r3"

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

FILES_${PN} += "${sysconfdir}/fonts/conf.d/ ${datadir}/fontconfig/"
