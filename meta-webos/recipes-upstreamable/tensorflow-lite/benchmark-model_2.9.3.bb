# Copyright (c) 2022 LG Electronics, Inc.

require tensorflow-lite_2.9.3.inc

DEPENDS += " \
    tensorflow-lite \
"

OECMAKE_TARGET_COMPILE = "benchmark_model"
CXXFLAGS += "-Wno-error=return-type"

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${B}/tools/benchmark/benchmark_model ${D}${bindir}
}

FILES:${PN} += "${bindir}/*"
