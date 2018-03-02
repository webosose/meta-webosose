# Copyright (c) 2014-2018 LG Electronics, Inc.

SUMMARY = "webOS Configuration Service data"
AUTHOR  = "SangWook Han <sangwook.han@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.0.0-1_eddb4920091287b38b6415984bfbcc85a6934b22"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${sysconfdir}/configd
    cp -vrf ${S}/configs/* ${D}${sysconfdir}/configd
}
