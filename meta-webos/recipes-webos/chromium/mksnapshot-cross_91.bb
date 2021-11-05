# Copyright (c) 2021 LG Electronics, Inc.

inherit cross
inherit pkgconfig

WEBRUNTIME_REPO_VERSION = "91"
require webruntime-common.inc
require webruntime-repo${REPO_VERSION}.inc

# Intentionaly disable uninative it failed to execute x86 binary on x64 host
# 32bit executable mksnapshot is required to create snapshot for the 32bit target
UNINATIVE_LOADER = ""

PROVIDES = "mksnapshot-cross-${TARGET_ARCH}"
PN = "mksnapshot-cross-${TARGET_ARCH}"
BPN = "mksnapshot"
PR = "r1"

TARGET = "v8_snapshot_clang_${TARGET_CPU}/mksnapshot"

DEPENDS = "glib-2.0-native python-native gcc-runtime"

GN_ARGS_append = "\
    use_pmlog=false \
"

do_configure() {
    export GYP_CHROMIUM_NO_ACTION=1
    export PATH="${S}/depot_tools:$PATH"

    GN_ARGS="${GN_ARGS}"
    echo GN_ARGS is ${GN_ARGS}
    echo BUILD_TARGETS are ${TARGET}
    cd ${S}/src
    gn --root=${S}/src --dotfile=mksnapshot.gn gen ${OUT_DIR} --args="${GN_ARGS}"
}

do_install() {
    echo "Installing ${PN}"
    install -d ${D}${bindir}

    install ${OUT_DIR}/v8_snapshot_clang_${TARGET_CPU}/mksnapshot ${D}${bindir}/mksnapshot-cross-${TARGET_ARCH}
}
