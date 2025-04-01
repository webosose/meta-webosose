# Copyright (c) 2023-2025 LG Electronics, Inc.

inherit clang_cmake

require g-media-pipeline.bb

FILESEXTRAPATHS:prepend := "${THISDIR}/g-media-pipeline:"
WEBOS_REPO_NAME = "g-media-pipeline"

PR = "r5"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,libcxx"
DEPENDS:remove = "umediaserver media-resource-calculator"
DEPENDS += "umediaserver-clang media-resource-calculator-clang"

OECMAKE_TARGET_COMPILE = "gmp-player-client"

PKGCONFIG_DIR = "${datadir}/pkgconfig"

CXXFLAGS +=" \
    -I${STAGING_INCDIR}/media-resource-calculator-clang \
    -I${STAGING_INCDIR}/cbe \
"

do_configure:prepend() {
    ln -snf media-resource-calculator-clang.pc ${STAGING_LIBDIR}/pkgconfig/media-resource-calculator.pc
}

do_install() {
    install -d ${D}/${LIBCBE_DIR}
    cp -R --no-dereference --preserve=mode,links -v ${B}/src/mediaplayerclient/libgmp-player-client.so* ${D}/${LIBCBE_DIR}
    cp -R --no-dereference --preserve=mode,links -v ${B}/src/player/libgmp-player.so* ${D}/${LIBCBE_DIR}
    chown root:root ${D}/${LIBCBE_DIR}/*

    install -d ${D}/${PKGCONFIG_DIR}
    install -v -m 644 ${S}/src/mediaplayerclient/gmp-player-client.pc ${D}/${PKGCONFIG_DIR}/gmp-player-client-clang.pc

    install -d ${D}${includedir}/cbe/gmp
    install -v -m 644 \
        ${S}/src/player/Player.h \
        ${S}/src/player/PlayerTypes.h \
        ${S}/src/base/types.h \
        ${S}/src/log/log.h \
        ${S}/src/mediaresource/requestor.h \
        ${S}/src/service/service.h \
        ${S}/src/dsi/DSIGeneratorFactory.h \
        ${S}/src/dsi/DSIGenerator.h \
        ${S}/src/dsi/DSIGeneratorAAC.h \
        ${S}/src/playerfactory/ElementFactory.h \
        ${S}/src/playerfactory/PlayerFactory.h \
        ${S}/src/mediaplayerclient/MediaPlayerClient.h \
        ${S}/src/lunaserviceclient/LunaServiceClient.h \
        ${D}/${includedir}/cbe/gmp
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"

RDEPENDS:${PN} += "g-media-pipeline"
