# Copyright (c) 2013-2022 LG Electronics, Inc.

SUMMARY = "A userspace service that provides access to the webOS database"
SECTION = "webos/base"
AUTHOR = "Yogish S <yogish.s@lge.com>"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "icu glib-2.0 leveldb leveldb-tl boost"
DEPENDS:append:class-target = " luna-service2 pmloglib jemalloc gtest curl"

# db8 is also the provider for mojodb
PROVIDES = "mojodb"

# db8's upstart job requires stat
VIRTUAL-RUNTIME_stat ?= "stat"
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_stat} ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-tests:append:class-target = " ${VIRTUAL-RUNTIME_bash}"

WEBOS_VERSION = "3.2.0-22_455918e4a71249781f88d75c2785abf6f5819133"
PR = "r37"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_daemon
inherit webos_library
inherit webos_prerelease_dep

EXTRA_OECMAKE += "-DWEBOS_DB8_BACKEND:STRING='leveldb;sandwich' -DCMAKE_SKIP_RPATH:BOOL=TRUE"
EXTRA_OECMAKE:append:class-target = " -DWEBOS_CONFIG_BUILD_TESTS:BOOL=TRUE  -DUSE_PMLOG:BOOL=TRUE  -DBUILD_LS2:BOOL=TRUE -DWANT_PROFILING:BOOL=${@ 'true' if '${WEBOS_DISTRO_PRERELEASE}' != '' else 'false'}"
EXTRA_OECMAKE:append:class-native = " -DWEBOS_CONFIG_BUILD_TESTS:BOOL=FALSE -DUSE_PMLOG:BOOL=FALSE -DBUILD_LS2:BOOL=FALSE"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-tests"

FILES:${PN}-tests = "${libdir}/${BPN}/tests"
FILES:${PN} += "${webos_sysbus_datadir}"

BBCLASSEXTEND = "native"

# From http://gpro.lgsvl.com/190951
SRC_URI += "file://0001-CMakeLists.txt-explicitly-link-with-libatomic-to-fix.patch"
SRC_URI += "file://0001-MojOsInternal.h-update-for-glibc-2.34.patch"
