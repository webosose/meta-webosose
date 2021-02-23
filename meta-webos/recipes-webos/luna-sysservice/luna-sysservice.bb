# Copyright (c) 2012-2021 LG Electronics, Inc.

SUMMARY = "Provides preference, timezone and ringtone services"
AUTHOR = "Keith Derrick <keith.derrick@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

VIRTUAL-RUNTIME_ntp ?= "sntp"

DEPENDS = "luna-service2 libpbnjson qtbase uriparser libxml2 sqlite3 pmloglib nyx-lib libwebosi18n"

RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_ntp} tzcode"

WEBOS_VERSION = "4.4.0-14_6ea857a7da7bd2522e8cf227c3bb3b6a244e2f15"
PR = "r7"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_machine_dep
inherit webos_daemon

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install_append() {
    install -d ${D}${datadir}/localization/${BPN}
    cp -rf ${S}/resources ${D}/${datadir}/localization/${BPN}
}

FILES_${PN} += "${datadir}/localization/${BPN}"
