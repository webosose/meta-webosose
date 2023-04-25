# Copyright (c) 2023 LG Electronics, Inc.

inherit clang_cmake

require media-codec-interface.bb

WEBOS_REPO_NAME = "media-codec-interface"

PR = "r3"

FILESEXTRAPATHS:prepend := "${THISDIR}/media-codec-interface/:"
# http://gpro.lge.com/c/webosose/media-codec-interface/+/349078 chrono namespace changed
SRC_URI += "file://0001-chrono-namespace-changed.patch"

CXXFLAGS +=" \
    -I${STAGING_INCDIR}/media-resource-calculator-clang \
    -I${STAGING_INCDIR}/cbe \
"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'webruntime-libcxx', 'system-libcxx', d)}"
PACKAGECONFIG[webruntime-libcxx] = ",,chromium-toolchain-native chromium-stdlib"
PACKAGECONFIG[system-libcxx] = ",,llvm-native clang"
DEPENDS:remove = "media-resource-calculator umediaserver"
DEPENDS += "media-resource-calculator-clang umediaserver-clang"

PKGCONFIG_DIR = "${datadir}/pkgconfig"

do_configure:prepend() {
    [ -f ${STAGING_LIBDIR}/pkgconfig/media-resource-calculator-clang.pc ] && \
    mv -n ${STAGING_LIBDIR}/pkgconfig/media-resource-calculator-clang.pc ${STAGING_LIBDIR}/pkgconfig/media-resource-calculator.pc
}

do_install:append() {
    install -d ${D}/${LIBCBE_DIR}
    mv ${D}/${libdir}/*.so ${D}/${LIBCBE_DIR}
    sed -i '/^libdir=.*\/lib$/ s/$/\/cbe/' ${D}/${PKGCONFIG_DIR}/media-codec-interface.pc
}

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"
