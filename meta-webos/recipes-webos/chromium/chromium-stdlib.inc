# Copyright (c) 2023-2025 LG Electronics, Inc.

inherit clang_libc
inherit pkgconfig

require webruntime-common.inc

LICENSE = "Apache-2.0-with-LLVM-exception"

DEPENDS += "glib-2.0"

GN_ARGS += "libcxx_is_shared=true"
GN_ARGS_CLANG = "is_clang=true"
GN_ARGS_LIBCXX = "use_custom_libcxx=true"
GN_ARGS += "target_sysroot=\"${STAGING_DIR_TARGET}\""

EXTRA_OEGN = "--root=${S}/src --dotfile=libc++.gn"

TARGET = "libc++"

LIBCBE_DIR = "${libdir}/cbe"

do_install() {
    offset_libcxx="src/buildtools/third_party/libc++/trunk/include"
    offset_libcxxabi="src/buildtools/third_party/libc++abi/trunk/include"
    install -d ${D}${includedir}/c++/v1
    cp -R --no-dereference --preserve=mode,links -v ${S}/${offset_libcxx}/* ${S}/${offset_libcxxabi}/* ${D}${includedir}/c++/v1

    install -d ${D}/${LIBCBE_DIR}
    install -v -m 644 ${B}/${BUILD_TYPE}/libc++.so ${D}/${LIBCBE_DIR}
}

FILES:${PN} = " \
    ${LIBCBE_DIR}/libc++.so \
"

FILES:${PN}-dev = " \
    ${includedir} \
"
