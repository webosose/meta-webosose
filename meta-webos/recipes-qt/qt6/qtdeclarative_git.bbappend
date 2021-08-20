# Copyright (c) 2014-2021 LG Electronics, Inc.

inherit webos_qt_global

EXTENDPRAUTO_append = "webos70"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Upstream-Status: Submitted
SRC_URI_append_class-target = " \
    file://0001-Check-if-a-device-in-knownPointingDevices-is-destroy.patch \
    file://0002-qqmllistmodel-Fix-QObjects-setting-indestructible.patch \
"

# Upstream-Status: Inappropriate
SRC_URI_append = " \
    file://0001-Revert-use-boolean-for-when-property.patch \
    file://0002-Revert-Don-t-hide-the-inputMethod-when-finishing-the.patch \
"

# Needed to workaround qmllint error
SRC_URI_append_class-native = " \
    file://0001-Allow-warnings-from-qmllint.patch \
"

# Supplement tool for qmllint
inherit webos_qmake6_paths
DEPENDS_append_class-native = " python3-regex-native"
SRC_URI_append_class-native = " file://qmllint-supplement.py"
do_install_append_class-native() {
    install -m 755 ${WORKDIR}/qmllint-supplement.py ${D}${OE_QMAKE_PATH_QT_BINS}
}

# TODO: To workaround the build issue where a recipe that depends on
# qtdeclarative fails at do_configure with CMake errors like:
# The imported target "Qt6::qmltyperegistrar" references the file
# ".../recipe-sysroot/usr/libexec/qmltyperegistrar"
# The imported target "Qt6::qmldom" references the file
# ".../recipe-sysroot/usr/bin/qmldom"
SYSROOT_DIRS_append = " \
    ${bindir} \
    ${libexecdir} \
"
