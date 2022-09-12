# Copyright (c) 2023 LG Electronics, Inc.

inherit clang_cmake

require g-media-pipeline.bb

WEBOS_REPO_NAME = "g-media-pipeline"

PR = "r0"

DEPENDS += "chromium-toolchain-native chromium-stdlib"
DEPENDS:remove = "umediaserver media-resource-calculator"
DEPENDS += "umediaserver-clang media-resource-calculator-clang"

OECMAKE_TARGET_COMPILE = "gmp-player-client"

PKGCONFIG_DIR = "${datadir}/pkgconfig"

do_install() {
    install -d ${D}/${LIBCBE_DIR}
    install -v -m 644 ${B}/src/mediaplayerclient/libgmp-player-client.so ${D}/${LIBCBE_DIR}
    install -v -m 644 ${B}/src/player/libgmp-player.so ${D}/${LIBCBE_DIR}

    install -d ${D}/${PKGCONFIG_DIR}
    install -v -m 644 ${S}/src/mediaplayerclient/gmp-player-client.pc ${D}/${PKGCONFIG_DIR}

    install -d ${D}/${includedir}/gmp
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
        ${D}/${includedir}/gmp
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"

RDEPENDS:${PN} += "g-media-pipeline"
