# Copyright (c) 2013-2021 LG Electronics, Inc.

SUMMARY = "A userspace service that provides access to the webOS database"
SECTION = "webos/base"
AUTHOR = "Maksym Sditanov <maxim.sditanov@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "icu glib-2.0 leveldb leveldb-tl boost"
DEPENDS_append_class-target = " luna-service2 pmloglib jemalloc gtest curl"

# db8 is also the provider for mojodb
PROVIDES = "mojodb"

# db8's upstart job requires stat
VIRTUAL-RUNTIME_stat ?= "stat"
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_stat} ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-tests_append_class-target = " ${VIRTUAL-RUNTIME_bash}"

WEBOS_VERSION = "3.2.0-15_d800ccad05c94aa79420f185ba3f87d24c1dd295"
PR = "r35"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_daemon
inherit webos_library
inherit webos_prerelease_dep

EXTRA_OECMAKE += "-DWEBOS_DB8_BACKEND:STRING='leveldb;sandwich' -DCMAKE_SKIP_RPATH:BOOL=TRUE"
EXTRA_OECMAKE_append_class-target = " -DWEBOS_CONFIG_BUILD_TESTS:BOOL=TRUE  -DUSE_PMLOG:BOOL=TRUE  -DBUILD_LS2:BOOL=TRUE -DWANT_PROFILING:BOOL=${@ 'true' if '${WEBOS_DISTRO_PRERELEASE}' != '' else 'false'}"
EXTRA_OECMAKE_append_class-native = " -DWEBOS_CONFIG_BUILD_TESTS:BOOL=FALSE -DUSE_PMLOG:BOOL=FALSE -DBUILD_LS2:BOOL=FALSE"

# Backported from Yocto 1.8
# http://git.openembedded.org/openembedded-core/commit/?id=79144da00f005b5a3ab8f7404730216cfc684616
OECMAKE_AR ?= "${AR}"
cmake_do_generate_toolchain_file_append() {
        cat >> ${WORKDIR}/toolchain.cmake <<EOF
set( CMAKE_AR ${OECMAKE_AR} CACHE FILEPATH "Archiver" )
EOF
}

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-tests"

FILES_${PN}-tests = "${libdir}/${BPN}/tests"
FILES_${PN} += "${webos_sysbus_datadir}"

BBCLASSEXTEND = "native"

# From http://gpro.lgsvl.com/190951
SRC_URI += "file://0001-CMakeLists.txt-explicitly-link-with-libatomic-to-fix.patch"
SRC_URI += "file://0001-MojOsInternal.h-update-for-glibc-2.34.patch"
