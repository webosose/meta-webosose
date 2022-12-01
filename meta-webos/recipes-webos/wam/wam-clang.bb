# Copyright (c) 2023 LG Electronics, Inc.

require wam.bb

inherit clang_cmake

BPN = "wam"

PROVIDES = "virtual/webappmanager-webos"

WEBOS_REPO_NAME = "wam"

PR = "r2"

FILESEXTRAPATHS:prepend := "${THISDIR}/wam:"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,llvm-native clang"
DEPENDS:remove = "jsoncpp"
DEPENDS += "jsoncpp-clang"
DEPENDS:remove = "gtest googletest"
DEPENDS += "googletest-clang"

OECMAKE_CXX_FLAGS += "\
    -Wno-error=unused-command-line-argument \
    -Wno-error=inconsistent-missing-override \
    -Wno-format \
"

PKGCONFIG_DIR = "${libdir}/pkgconfig"

do_compile:prepend() {
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/' ${RECIPE_SYSROOT}/${PKGCONFIG_DIR}/gtest.pc
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/' ${RECIPE_SYSROOT}/${PKGCONFIG_DIR}/gmock.pc
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/' ${RECIPE_SYSROOT}/${PKGCONFIG_DIR}/gmock_main.pc
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/' ${RECIPE_SYSROOT}/${PKGCONFIG_DIR}/gtest_main.pc
    rm -rf ${STAGING_LIBDIR}/libgtest*.a
    rm -rf ${STAGING_LIBDIR}/libgmock*.a
}
