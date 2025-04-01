# Copyright (c) 2014-2025 LG Electronics, Inc.

inherit webos_qt_global

EXTENDPRAUTO:append = "webos88"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

PATCHTOOL = "git"

# Upstream-Status: Backport
SRC_URI:append = " \
    file://0001-Correctly-handle-QQuickState-when.patch;maxver=6.2.3 \
"

# Upstream-Status: Submitted
SRC_URI:append = " \
    file://0001-Check-if-a-device-in-knownPointingDevices-is-destroy.patch;maxver=${WEBOS_PATCH_MAXVER} \
"

# Upstream-Status: Pending
SRC_URI:append = " \
    file://0002-Fix-build-issue-with-uic.patch;minver=6.7.0 \
"

# Upstream-Status: Inappropriate
SRC_URI:append = " \
    file://9901-Revert-Don-t-hide-the-inputMethod-when-finishing-the.patch;maxver=${WEBOS_PATCH_MAXVER} \
"

# Supplement tool for qmllint
inherit webos_qmake6_paths
DEPENDS:append:class-native = " python3-regex-native"
SRC_URI:append:class-native = " file://qmllint-supplement.py"
do_install:append:class-native() {
    install -m 755 ${UNPACKDIR}/qmllint-supplement.py ${D}${OE_QMAKE_PATH_QT_BINS}
}

# TODO: To workaround the build issue where a recipe that depends on
# qtdeclarative fails at do_configure with CMake errors like:
# The imported target "Qt6::qmltyperegistrar" references the file
# ".../recipe-sysroot/usr/libexec/qmltyperegistrar"
# The imported target "Qt6::qmldom" references the file
# ".../recipe-sysroot/usr/bin/qmldom"
SYSROOT_DIRS:append = " \
    ${bindir} \
    ${libexecdir} \
"
