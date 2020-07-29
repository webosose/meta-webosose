# Copyright (c) 2014-2020 LG Electronics, Inc.

inherit webos_qmake5

EXTENDPRAUTO_append = "webos57"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Patches from 5.12.meta-webos.29 based on 5.12.meta-qt5.2
SRC_URI_append_class-target = " \
    file://0001-Set-the-stencil-buffer-zone.patch \
    file://0002-Add-clipNext-null-pointer-guard.patch \
    file://0003-Prevent-items-from-being-deleted-while-removing.patch \
    file://0004-webOS-Share-images-even-if-cache-is-false.patch \
    file://0005-Fix-for-possible-crash-in-QSGDefaultLayer-grab.patch \
    file://0006-Fix-outline-for-connected-text.patch \
    file://0007-Warn-circular-dependency-when-loading-types.patch \
    file://0008-Fix-baseUrl-returning-an-empty-url.patch \
    file://0009-webOS-Don-t-send-synthetic-hover-event-on-a-frame-up.patch \
    file://0010-Remove-null-pointer-checks-for-this-from-QQmlContext.patch \
    file://0011-QQuickWindow-Consider-z-order-of-children-when-deliv.patch \
    file://0012-webOS-Allow-to-have-activeFocus-for-each-window.patch \
    file://0013-Fix-nullptr-handling-in-binding.patch \
    file://0014-Fix-QQuickKeyNavigationAttached-issue.patch \
"

SRC_URI_append_qemux86 = " \
    file://0098-Revert-Fix-failing-assertion-in-the-GC-with-JIT.patch \
    file://0099-Revert-Fix-failing-assertion-in-the-GC-with-the-JIT.patch \
"

SRC_URI_append_qemux86-64 = " \
    file://0098-Revert-Fix-failing-assertion-in-the-GC-with-JIT.patch \
    file://0099-Revert-Fix-failing-assertion-in-the-GC-with-the-JIT.patch \
"

# Supplement tool for qmllint
DEPENDS_append_class-native = " python3-regex-native"
SRC_URI_append_class-native = " file://qmllint-supplement.py"
do_install_append_class-native() {
    install -m 755 ${WORKDIR}/qmllint-supplement.py ${D}${OE_QMAKE_PATH_QT_BINS}
}

SRC_URI_append = " \
    file://0026-Use-python3-explicitly.patch \
"
