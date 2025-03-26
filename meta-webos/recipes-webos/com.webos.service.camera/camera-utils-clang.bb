# Copyright (c) 2024-2025 LG Electronics, Inc.

SUMMARY = "Utility library for camera service"
AUTHOR = "Sungho Lee <shl.lee@lge.com>"
SECTION = "webos/libs"

inherit clang_cmake

require com.webos.service.camera.inc

WEBOS_REPO_NAME = "com.webos.service.camera"

S = "${WORKDIR}/git/src/libs"

PR = "${INC_PR}.3"

DEPENDS = "glib-2.0 luna-service2 pmloglib nlohmann-json"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,libcxx"

do_install:append() {
    install -d ${D}/${LIBCBE_DIR}
    mv ${D}/${libdir}/*.so* ${D}/${LIBCBE_DIR}
    mv -n ${D}${includedir}/camera ${D}${includedir}/camera-clang
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"
