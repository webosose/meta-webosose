# Copyright (c) 2017-2020 LG Electronics, Inc.

SUMMARY = "Maliit Input Method Plugins"
DESCRIPTION = "Mallit-based virtual keyboard and input method engine for open webOS"
AUTHOR = "Pugalendhi Ganesan <pugalendhi.ganesan@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "qtbase luna-service2 luna-prefs qt-features-webos qtdeclarative maliit-framework-webos qtdeclarative-native"
RDEPENDS_${PN} += "libhangul sunpinyin pyzy qml-webos-bridge openwnn-webos"

WEBOS_VERSION = "1.0.0-11_8f4114eec04643bbf7c0adc413323a8b27cd791e"
PR = "r3"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_qmake5
inherit webos_system_bus
inherit webos_public_repo

WEBOS_REPO_NAME = "ime-manager"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

EXTRA_QMAKEVARS_PRE += "CONFIG+=disable-tests"
EXTRA_QMAKEVARS_PRE += "CONFIG+=enable-maliit-plugin-chinese"
EXTRA_QMAKEVARS_PRE += "CONFIG+=enable-maliit-plugin-japanese"

EXTRA_QMAKEVARS_PRE += "LIBDIR=${STAGING_LIBDIR}"
EXTRA_QMAKEVARS_PRE += "WEBOS_INSTALL_BINS=${sbindir}"
EXTRA_QMAKEVARS_PRE += "MALIIT_PLUGIN_VERSION=${PV}"

FILES_${PN} += "${libdir}/maliit ${datadir}/maliit"
