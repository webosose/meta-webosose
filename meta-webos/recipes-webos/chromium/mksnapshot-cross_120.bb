# Copyright (c) 2024-2025 LG Electronics, Inc.

inherit cross
inherit pkgconfig

WEBRUNTIME_REPO_VERSION = "120"
require webruntime-common.inc
require webruntime-repo${REPO_VERSION}.inc

# Intentionaly disable uninative it failed to execute x86 binary on x64 host
# 32bit executable mksnapshot is required to create snapshot for the 32bit target
UNINATIVE_LOADER = ""

# M114: To avoid error "--dynamic-linker=: must take a non-empty argument" | [http://repo.lge.com:8080/c/neva/meta-lg-webos-neva/+/9221]
# append is added to work-around mismatch of basebash
BUILD_LDFLAGS:append = " -Wl,--allow-shlib-undefined -Wl,--dynamic-linker="
BUILD_LDFLAGS:remove = " -Wl,--allow-shlib-undefined -Wl,--dynamic-linker="

PROVIDES = "mksnapshot-cross-${TARGET_ARCH}"
PN = "mksnapshot-cross-${TARGET_ARCH}"
BPN = "mksnapshot"
PR = "r0"

TARGET = "v8_snapshot_clang_${TARGET_CPU}/mksnapshot"

DEPENDS = "glib-2.0-native gcc-runtime"

GN_ARGS:append = "\
    use_pmlog=false \
"

EXTRA_OEGN = "--root=${S}/src --dotfile=mksnapshot.gn"

do_install() {
    echo "Installing ${PN}"
    install -d ${D}${bindir}
    install ${OUT_DIR}/v8_snapshot_clang_${TARGET_CPU}/mksnapshot ${D}${bindir}/mksnapshot-cross-${TARGET_ARCH}
}
