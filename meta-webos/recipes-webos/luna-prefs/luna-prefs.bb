# Copyright (c) 2012-2022 LG Electronics, Inc.

SUMMARY = "webOS preferences manager"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "luna-service2 json-c sqlite3 glib-2.0 nyx-lib"
RDEPENDS:${PN} = "luna-prefs-data"

WEBOS_VERSION = "3.0.0-9_9e4e6c9d93d4acdbc48be3f0bb3467c2a56332f0"
PR = "r14"

#inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_program
inherit webos_library
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install:append() {
    # CFISH-930: remove "other" perms granted by pmmakefiles (aka palmmake):
    chmod o-rwx ${D}${bindir}/luna-prefs-service
    chmod o-rwx ${D}${bindir}/lunaprop
}
