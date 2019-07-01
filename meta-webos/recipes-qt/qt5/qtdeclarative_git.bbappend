# Copyright (c) 2014-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos38"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

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
    file://0013-Allow-fixed-animation-step-in-QSGGuiThreadRenderLoop.patch \
    file://0014-Use-the-QT_DISTANCEFIELD_HIGHGLYPHCOUNT-function-fro.patch \
    file://0015-Check-engine-to-mark-Object-for-garbage-collection.patch \
    file://0016-Images-can-be-shared-even-if-cache-is-false.patch \
    file://0017-QQuickWindow-Add-invalidateCursorItem.patch \
    file://0018-TimeZone-fix-for-QML-Date-object.patch \
    file://0019-Fix-for-possible-crash-in-QSGDefaultLayer-grab.patch \
    file://0020-QQuickTextEdit-Fix-cursor-movement-in-compose-mode.patch \
    file://0021-Fix-for-Hindi-glyph-break.patch \
    file://0022-Warn-circular-dependency-when-loading-types.patch \
    file://0023-Fix-baseUrl-returning-an-empty-url.patch \
    file://0024-Rebuild-QQmlData-propertyCache-if-deleted-by-another.patch \
    file://0025-Add-QQmlEngine-to-QQuickPixmapKey.patch \
"
