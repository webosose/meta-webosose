# Copyright (c) 2020-2024 LG Electronics, Inc.

SUMMARY = "Unified search service"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "luna-service2 libpbnjson glib-2.0 procps sqlite3"

WEBOS_VERSION = "1.0.0-10_d48cc0481448810de233b91aa8a83989b652881c"
PR = "r4"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_public_repo
inherit webos_system_bus

# http://gpro.lge.com/c/webosose/com.webos.service.unifiedsearch/+/347405 Fix build with gcc-12
# http://gpro.lge.com/c/webosose/com.webos.service.unifiedsearch/+/347406 CMakeLists.txt: update from libprocps to libproc2
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-Fix-build-with-gcc-12.patch \
"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "unifiedsearch.service"

PACKAGES += "${PN}-plugins"
FILES:${PN}-plugins = "${libdir}/plugins"

EXTRA_OECMAKE += "-DUSE_BUILTIN_PLUGIN:bool=true"
