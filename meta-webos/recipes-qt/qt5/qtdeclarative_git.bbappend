# Copyright (c) 2014-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos38"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Patches from 5.11.meta-webos.12 based on 5.11.meta-qt5.4
SRC_URI_append_class-target = " \
    file://0001-Revert-Remove-dead-code.patch \
    file://0002-Update-timezone-info-before-localtime_r.patch \
    file://0003-LTTNG-tracing-support-in-Qt-Quick.patch \
    file://0004-Set-the-stencil-buffer-zone.patch \
    file://0005-Prevent-items-from-being-deleted-while-removing.patch \
    file://0006-Revert-Make-QQuickKeyNavigationAttached-respect-user.patch \
    file://0007-Preserve-render-loop-OpenGL-context.patch \
    file://0008-Revert-Make-SmoothedAnimation-and-SpringAnimation-sm.patch \
    file://0009-do-not-call-windowDestroyed-when-cleanup.patch \
    file://0010-Allow-to-abort-the-scene-graph-rendering-of-QML.patch \
    file://0011-Fix-incorrect-listview-footer-position-on-empty-mode.patch \
    file://0012-Correct-pixelRatio-for-glyphs.patch \
    file://0013-Images-can-be-shared-even-if-cache-is-false.patch \
    file://0014-QQuickWindow-Add-invalidateCursorItem.patch \
    file://0015-TimeZone-fix-for-QML-Date-object.patch \
    file://0016-Fix-for-possible-crash-in-QSGDefaultLayer-grab.patch \
    file://0017-QQuickTextEdit-Fix-cursor-movement-in-compose-mode.patch \
    file://0018-Fix-for-Hindi-glyph-break.patch \
    file://0019-Warn-circular-dependency-when-loading-types.patch \
    file://0020-Fix-baseUrl-returning-an-empty-url.patch \
    file://0021-Don-t-send-synthetic-hover-event-on-a-frame-update.patch \
    file://0022-Add-guard-to-avoid-recursively-text-layout-call.patch \
    file://0023-QQuickItemView-Remove-redundant-refill-when-layout.patch \
    file://0024-Add-QQmlEngine-to-QQuickPixmapKey.patch \
"
