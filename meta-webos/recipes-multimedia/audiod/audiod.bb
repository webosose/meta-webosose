# Copyright (c) 2014-2020 LG Electronics, Inc.

SUMMARY = "webOS Audiod daemon and utilities"
AUTHOR = "Manohar Babu <manohar.babu@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 libpbnjson luna-service2 pmloglib luna-prefs boost pulseaudio"
RDEPENDS_${PN} = "\
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

WEBOS_VERSION = "1.0.0-17_674cc0a2121ddea38f65513b1f1c8c63b7f1c2f9"
PR = "r28"

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

WEBOS_REPO_NAME = "audiod-pro"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE += "${@bb.utils.contains('WEBOS_LTTNG_ENABLED', '1', '-DWEBOS_LTTNG_ENABLED:BOOLEAN=True', '', d)}"

EXTRA_OECMAKE += "-DAUDIOD_PALM_LEGACY:BOOLEAN=True"
EXTRA_OECMAKE += "-DAUDIOD_TEST_API:BOOLEAN=True"

FILES_${PN} += "${datadir}/alsa/"
FILES_${PN} += "/data"
FILES_${PN} += "${webos_mediadir}/internal"

# From http://gpro.lge.com/c/webosose/audiod-pro/+/266294
SRC_URI += "file://0001-CMakeLists.txt-Drop-2nd-call-to-find_package-Boost.patch"

SRC_URI += "file://0001-Fix-build-with-boost-1.73.0.patch"
