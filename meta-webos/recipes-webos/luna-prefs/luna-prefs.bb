# Copyright (c) 2012-2018 LG Electronics, Inc.

SUMMARY = "webOS preferences manager"
AUTHOR = "Oleksandr Ivanov <oleksandr.ivanov@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "luna-service2 cjson sqlite3 glib-2.0 nyx-lib"
RDEPENDS_${PN} = "luna-prefs-data"

WEBOS_VERSION = "3.0.0-1_0827814ccdc5ebe2b372d90120427043cc6ac94e"
PR = "r12"

#inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_program
inherit webos_library
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install_append() {
    # CFISH-930: remove "other" perms granted by pmmakefiles (aka palmmake):
    chmod o-rwx ${D}${bindir}/luna-prefs-service
    chmod o-rwx ${D}${bindir}/lunaprop

}

