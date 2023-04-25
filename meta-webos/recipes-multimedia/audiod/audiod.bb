# Copyright (c) 2014-2023 LG Electronics, Inc.

SUMMARY = "webOS Audiod daemon and utilities"
AUTHOR = "Sushovan G <sushovan.g@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 libpbnjson luna-service2 pmloglib luna-prefs boost pulseaudio"
RDEPENDS:${PN} = "\
    libasound \
    libasound-module-pcm-pulse \
    libpulsecore \
    pulseaudio \
    pulseaudio-lib-cli \
    pulseaudio-lib-protocol-cli \
    pulseaudio-misc \
    pulseaudio-module-cli-protocol-tcp \
    pulseaudio-module-cli-protocol-unix \
    pulseaudio-server \
"

WEBOS_VERSION = "1.0.0-60_47d48fa0852af5cdde52044f8fd96a49a272056c"
PR = "r34"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_daemon
inherit webos_system_bus
inherit webos_machine_dep
inherit gettext
inherit webos_lttng
inherit webos_public_repo

# [http://gpro.lge.com/c/webosose/audiod-pro/+/344272 events.h: add missing functional include]
# [http://gpro.lge.com/c/webosose/audiod-pro/+/348186 Fix luna-service2 usage]
WEBOS_REPO_NAME = "audiod-pro"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-events.h-add-missing-functional-include.patch \
    file://0002-Fix-luna-service2-usage.patch \
"
S = "${WORKDIR}/git"

EXTRA_OECMAKE += "${@bb.utils.contains('WEBOS_LTTNG_ENABLED', '1', '-DWEBOS_LTTNG_ENABLED:BOOLEAN=True', '', d)}"

EXTRA_OECMAKE += "-DAUDIOD_PALM_LEGACY:BOOLEAN=True"
EXTRA_OECMAKE += "-DAUDIOD_TEST_API:BOOLEAN=True"

FILES:${PN} += "${datadir}/systemsounds"
