# Copyright (c) 2023 LG Electronics, Inc.

AUTHOR = "Donghyun Kim <donghyun11.kim@lge.com>"

inherit cross
inherit pkgconfig

WEBRUNTIME_REPO_VERSION = "108"
require webruntime-common.inc
require webruntime-repo${REPO_VERSION}.inc

# Intentionaly disable uninative it failed to execute x86 binary on x64 host
# 32bit executable mksnapshot is required to create snapshot for the 32bit target
UNINATIVE_LOADER = ""

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
