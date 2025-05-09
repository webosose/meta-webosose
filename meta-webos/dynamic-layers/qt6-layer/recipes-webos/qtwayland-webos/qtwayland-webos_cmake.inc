# Copyright (c) 2023-2025 LG Electronics, Inc.

inherit webos_cmake_qt6

FILES:${PN}-dev += " \
    ${QT6_INSTALL_DESCRIPTIONSDIR} \
    ${QT6_INSTALL_LIBDIR}/cmake/* \
    ${QT6_INSTALL_LIBDIR}/metatypes/* \
    ${QT6_INSTALL_LIBDIR}/sbom/* \
"

# http://gpro.lge.com/c/webosose/qtwayland-webos/+/349070 Use QtWebOSEglClientBuffer for cmake]
# Does it have to be applied only for CMake build?
SRC_URI:append = "\
    file://0001-Use-QtWebOSEglClientBuffer-for-cmake.patch \
"

PACKAGECONFIG[criu] = "-DFEATURE_criu=ON,-DFEATURE_criu=OFF,criu-webos"
PACKAGECONFIG[lttng] = "-DFEATURE_lttng=ON,-DFEATURE_lttng=OFF,"
