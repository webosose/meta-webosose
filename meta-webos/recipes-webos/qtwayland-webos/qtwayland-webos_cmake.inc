# Copyright (c) 2023 LG Electronics, Inc.

inherit webos_cmake_qt6

FILES:${PN}-dev += " \
    ${libdir}/cmake/* \
    ${libdir}/metatypes/* \
    ${datadir}/modules/* \
"

SRC_URI:append = "\
    file://0001-Use-QtWebOSEglClientBuffer-for-cmake.patch \
"

PACKAGECONFIG[criu] = "-DFEATURE_criu=ON,-DFEATURE_criu=OFF,criu-webos"
PACKAGECONFIG[lttng] = "-DFEATURE_lttng=ON,-DFEATURE_lttng=OFF,"

