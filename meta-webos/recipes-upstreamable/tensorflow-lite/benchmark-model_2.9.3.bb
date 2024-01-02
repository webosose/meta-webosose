# Copyright (c) 2022-2024 LG Electronics, Inc.

require tensorflow-lite_2.9.3.inc

inherit pkgconfig

PR = "r4"

DEPENDS += " \
    tensorflow-lite \
"

SRC_URI += " \
    file://0001-add-auto-delegation-option.patch \
    file://0002-Enable-NNAPI-Options-in-benchmark_model-test.patch \
"

PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'auto-acceleration', 'ads', '', d)}"

PACKAGECONFIG[ads] = "-DENABLE_AUTO_DELEGATE=ON,-DENABLE_AUTO_DELEGATE=OFF,tflite-auto-delegation"

OECMAKE_TARGET_COMPILE = "benchmark_model"

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${B}/tools/benchmark/benchmark_model ${D}${bindir}
}

FILES:${PN} += "${bindir}"
