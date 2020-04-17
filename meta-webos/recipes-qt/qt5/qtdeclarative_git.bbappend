# Copyright (c) 2014-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos50"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Patches from 5.12.meta-webos.22 based on 5.12.meta-qt5.4
SRC_URI_append_class-target = " \
    file://0001-LTTNG-tracing-support-in-Qt-Quick.patch \
    file://0002-Set-the-stencil-buffer-zone.patch \
    file://0003-Prevent-items-from-being-deleted-while-removing.patch \
    file://0004-do-not-call-windowDestroyed-when-cleanup.patch \
    file://0005-Fix-incorrect-listview-footer-position-on-empty-mode.patch \
    file://0006-Images-can-be-shared-even-if-cache-is-false.patch \
    file://0007-QQuickWindow-Add-invalidateCursorItem.patch \
    file://0008-Fix-for-possible-crash-in-QSGDefaultLayer-grab.patch \
    file://0009-QQuickTextEdit-Fix-cursor-movement-in-compose-mode.patch \
    file://0010-Fix-for-Hindi-glyph-break.patch \
    file://0011-Warn-circular-dependency-when-loading-types.patch \
    file://0012-Fix-baseUrl-returning-an-empty-url.patch \
    file://0013-Don-t-send-synthetic-hover-event-on-a-frame-update.patch \
    file://0014-QQuickItemView-Remove-redundant-refill-when-layout.patch \
    file://0015-Remove-null-pointer-checks-for-this-from-QQmlContext.patch \
    file://0016-Add-QQmlEngine-to-QQuickPixmapKey.patch \
    file://0017-QQuickWindow-Consider-z-order-of-children-when-deliv.patch \
    file://0018-Allow-to-have-activeFocus-for-each-window.patch \
    file://0019-Fix-nullptr-handling-in-binding.patch \
    file://0020-V4-Check-for-exceptions-before-we-use-the-result-of-.patch \
    file://0021-ArrayPrototype-method_filter-Check-for-exception-aft.patch \
    file://0022-Fix-failing-assertion-in-the-GC-with-the-JIT.patch \
    file://0023-Fix-failing-assertion-in-the-GC-with-JIT.patch \
    file://0024-Fix-QQuickKeyNavigationAttached-issue.patch \
"

# Supplement tool for qmllint
DEPENDS_append_class-native = " python3-regex-native"
SRC_URI_append_class-native = " file://qmllint-supplement.py"
do_install_append_class-native() {
    install -m 755 ${WORKDIR}/qmllint-supplement.py ${D}${OE_QMAKE_PATH_QT_BINS}
}
