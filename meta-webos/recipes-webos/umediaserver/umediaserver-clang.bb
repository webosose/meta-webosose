# Copyright (c) 2023-2024 LG Electronics, Inc.

inherit clang_cmake

require umediaserver.inc

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,llvm-native clang"

WEBOS_REPO_NAME = "umediaserver"

PR = "${INC_PR}.3"

OECMAKE_CXX_FLAGS += "-Wno-c++11-narrowing -Wno-format-security"
OECMAKE_TARGET_COMPILE = "umedia_api resource_mgr_client resource_mgr_client_c"

PKGCONFIG_DIR = "${datadir}/pkgconfig"

do_install() {
    install -d ${D}/${LIBCBE_DIR}

    # Webruntime links to *.so.{MAJOR_VERSION} in runtime so we need all solinks installed
    oe_soinstall ${B}/src/media_client/libumedia_api.so.${WEBOS_COMPONENT_VERSION} ${D}/${LIBCBE_DIR}
    oe_soinstall ${B}/src/ums_connector/libums_connector_impl.so.${WEBOS_COMPONENT_VERSION} ${D}/${LIBCBE_DIR}
    oe_soinstall ${B}/src/ums_connector/libums_connector.so.${WEBOS_COMPONENT_VERSION} ${D}/${LIBCBE_DIR}
    oe_soinstall ${B}/src/resource_manager/libresource_mgr_client.so.${WEBOS_COMPONENT_VERSION} ${D}/${LIBCBE_DIR}
    oe_soinstall ${B}/src/resource_manager/libresource_mgr_client_c.so.${WEBOS_COMPONENT_VERSION} ${D}/${LIBCBE_DIR}

    install -d ${D}/${PKGCONFIG_DIR}
    install -v -m 644 ${B}/src/media_client/umedia_api.pc ${D}/${PKGCONFIG_DIR}/umedia_api_clang.pc
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/' ${D}/${PKGCONFIG_DIR}/umedia_api_clang.pc

    install -d ${D}${includedir}/cbe
    install -v -m 644 ${S}/include/public/*.h ${D}${includedir}/cbe
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"

RDEPENDS:${PN} += "libpbnjson-clang"
