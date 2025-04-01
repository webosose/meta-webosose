# Copyright (c) 2023-2025 LG Electronics, Inc.

inherit clang_cmake

require libpbnjson.bb

WEBOS_REPO_NAME = "libpbnjson"

PR = "r5"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,libcxx"

OECMAKE_TARGET_COMPILE = "pbnjson_cpp"

# For unknown reason ld.lld messes native & target sysroot.
# It searches for libgmp.so in recipe-sysroot-native
TOOLCHAIN_OPTIONS:remove = "-fuse-ld=${STAGING_BINDIR_NATIVE}/ld.lld"

# This lets cmake install work without triggering build of target pbnjson_validate which depends on boost
EXTRA_OECMAKE += "-DCMAKE_SKIP_INSTALL_ALL_DEPENDENCY:BOOL=TRUE"

PKGCONFIG_DIR = "${datadir}/pkgconfig"

do_install:append() {
    install -d ${D}/${LIBCBE_DIR}
    mv ${D}/${libdir}/*.so ${D}/${libdir}/*.so.* ${D}/${LIBCBE_DIR}

    install -d ${D}${includedir}/cbe/${BPN}
    mv -n ${D}${includedir}/pbnjson ${D}${includedir}/cbe/${BPN}
    mv -n ${D}${includedir}/*.h* ${D}${includedir}/cbe/${BPN}

    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/; /^libdir=.*\/lib32$/ s/$/\/cbe/; /^libdir=.*\/lib64$/ s/$/\/cbe/;' ${D}/${PKGCONFIG_DIR}/pbnjson_c.pc
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/; /^libdir=.*\/lib32$/ s/$/\/cbe/; /^libdir=.*\/lib64$/ s/$/\/cbe/;' ${D}/${PKGCONFIG_DIR}/pbnjson_cpp.pc

    mv -n ${D}/${PKGCONFIG_DIR}/pbnjson_c.pc ${D}/${PKGCONFIG_DIR}/pbnjson_c_clang.pc
    mv -n ${D}/${PKGCONFIG_DIR}/pbnjson_cpp.pc ${D}/${PKGCONFIG_DIR}/pbnjson_cpp_clang.pc
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"

# Avoid conflict with libpbnjson recipe
PRIVATE_LIBS = "libpbnjson_c.so.* libpbnjson_cpp.so.*"
