# Copyright (c) 2014-2024 LG Electronics, Inc.

SUMMARY = "webOS Configuration Service"
AUTHOR = "Guruprasad KN <guruprasad.kn@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "luna-service2 pmloglib glib-2.0 libpbnjson gtest"
RDEPENDS:${PN} += "configd-data"

WEBOS_VERSION = "1.2.0-24_b3864ffd57d4c1ffc2bb59cbea014e6338ea5bae"
PR = "r22"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_daemon
inherit webos_machine_dep
inherit webos_distro_variant_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-Logger-fix-segfaults-with-64bit-time_t.patch \
"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "configd.service"

PACKAGES =+ "${PN}-tests"
FILES:${PN}-tests = "${libexecdir}/tests/*"
FILES:${PN} += "${webos_sysbus_datadir}"

# http://gecko.lge.com:8000/Errors/Details/821712
# configd/1.2.0-20/git/src/libconfigd/libconfigd.c:335:17: error: passing argument 4 of 'LSCall' from incompatible pointer type [-Wincompatible-pointer-types]
# configd/1.2.0-20/git/src/libconfigd/libconfigd.c:870:13: error: passing argument 4 of 'LSCallOneReply' from incompatible pointer type [-Wincompatible-pointer-types]
# configd/1.2.0-20/git/src/libconfigd/libconfigd.c:939:29: error: passing argument 4 of 'LSCallOneReply' from incompatible pointer type [-Wincompatible-pointer-types]
# configd/1.2.0-20/git/src/libconfigd/libconfigd.c:970:25: error: passing argument 4 of 'LSCallOneReply' from incompatible pointer type [-Wincompatible-pointer-types]
CFLAGS += "-Wno-error=incompatible-pointer-types"
