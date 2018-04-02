# Copyright (c) 2013-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos64"

# Remove LGPL3-only files
python do_patch_append() {
    bb.build.exec_func('remove_LGPL3', d)
}

remove_LGPL3() {
    rm -vf ${S}/src/plugins/platforms/android/extract.cpp
}

# Disable features we don't use in all webOS products
PACKAGECONFIG_DEFAULT_remove = "dbus"

# Configure qt5 to use platform harfbuzz
PACKAGECONFIG_append = " harfbuzz"
PACKAGECONFIG[harfbuzz] = "-system-harfbuzz,-qt-harfbuzz,harfbuzz"

# Configure qt5 to compile with GL ES2 instead of default desktop GL
PACKAGECONFIG_GL = "gles2"
# We have alsa in DISTRO_FEATURES so it was enabled before
PACKAGECONFIG_append = " alsa"
# We had this enabled in our old gpro/meta-qt5 recipe
PACKAGECONFIG_append = " iconv"
# We had this enabled in our old gpro/meta-qt5 recipe
PACKAGECONFIG_append = " xkb"
# We had this enabled in our old gpro/meta-qt5 recipe
PACKAGECONFIG_append = " icu"
# We had this enabled in our old gpro/meta-qt5 recipe
PACKAGECONFIG_append = " glib"
# We had this enabled in our old gpro/meta-qt5 recipe
PACKAGECONFIG_append = " fontconfig"
# We had this enabled in our old gpro/meta-qt5 recipe
PACKAGECONFIG_append = " sql-sqlite"

# XXX Change --linuxfb => -no-linuxfb
# PACKAGECONFIG_append = " linuxfb"
# remove linuxfb dependency
# PACKAGECONFIG[linuxfb] = "-linuxfb,-no-linuxfb,"

# we know we're not building any QPA now
PACKAGECONFIG_append = " no-qpa-platform"
PACKAGECONFIG[no-qpa-platform] = "-no-qpa-platform-guard,,"

# XXX Perhaps change -qt-pcre => -system-pcre (as it's already part of webOS)
# PACKAGECONFIG_append = " pcre"

# Depending on whether LTTNG support is enabled or not for the build we need to
# depend on the LTTNG providers to not let the build fail
inherit webos_lttng
PACKAGECONFIG[lttng] = "-lttng,-no-lttng,lttng-ust"
PACKAGECONFIG_append = "${@ ' lttng' if '${WEBOS_LTTNG_ENABLED}' == '1' else '' }"

# XXX Try -reduce-exports

# XXX maliit-framework-webos currently requires --dbus; change to -no-dbus and
#      remove dbus from PACKAGECONFIG variable once [GF-8182] is implemented.
# PACKAGECONFIG = " \
#    release \
#    udev \
#    evdev \
#    widgets \
#    openssl \
#    ${PACKAGECONFIG_GL} \
#    ${PACKAGECONFIG_FB} \
#    ${PACKAGECONFIG_X11} \
#    ${PACKAGECONFIG_FONTS} \
#    ${PACKAGECONFIG_SYSTEM} \
#    ${PACKAGECONFIG_MULTIMEDIA} \
#    ${PACKAGECONFIG_DISTRO} \
#"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

SRC_URI_append = " \
    file://0001-WebOS-platform-expects-filenames-in-UTF-8.patch \
    file://0002-DisableCertificateVerificationCheck.patch \
    file://0003-ChangeTheGlyphCacheSize.patch \
    file://0004-QOpenGL2PaintEngineEx-must-makeCurrent-own-context.patch \
    file://0005-Decrease-the-size-of-QFontCache.patch \
    file://0006-platformsupport.pro-Add-MESA_EGL_NO_X11_HEADERS.patch \
    file://0007-Fix-floating-point-clip-rectangle-rounding-in-OpenGL.patch \
    file://0008-network-Send-SslHandShakeError-more-in-detail.patch \
    file://0009-Add-support-for-local-zh-Hant-MY.patch \
    file://0010-network-SSL-Handle-Multiple-Client-Certificates.patch \
    file://0011-Expose-new-QSystemSemaphore-resetOwner-API.patch \
    file://0012-Fix-allocateTimerId.patch \
    file://0013-Disable-code-cache-of-video-and-audio-file.patch \
    file://0014-LTTNG-tracing-support-in-QtGUI.patch \
    file://0015-QOpenGLTextureCache-size-setting.patch \
    file://0016-QOpenGLTextureGlyphCache-limits.patch \
    file://0017-QFontEngine-glyph-cache-count.patch \
    file://0018-QNetworkDiskCache-Disable-disk-cache-if-content-have.patch \
    file://0019-Revert-Don-t-accept-json-strings-with-trailing-garba.patch \
    file://0020-Revert-Make-QElapsedTimer-default-to-invalid-and-now.patch \
    file://0021-Revert-Fix-rendering-of-fonts-matched-based-on-stret.patch \
    file://0022-Preserve-OpenGL-context-on-window-close.patch \
    file://0023-Revert-Remove-QPlatformScreenPageFlipper.patch \
    file://0024-Add-the-accessiblebridge-as-a-plugintype-of-gui-modu.patch \
    file://0025-Fix-HarfBuzz-NG-regression.patch \
    file://0026-Support-wrapMode-wordWrap-for-QML-Text-type-for-Kore.patch \
    file://0027-Color-emoji-support.patch \
    file://0028-Fix-for-deferredDelete-bug-when-calling-the-glib-loo.patch \
    file://0029-Check-if-combined-glyph-exists-in-font-s-charmap-tab.patch \
    file://0030-Prevent-rare-segfault-crashes-in-QNetworkConfigurati.patch \
    file://0031-Add-environment-variable-QT_DISTANCEFIELD.patch \
    file://0032-Fix-distance-field-rendering-of-very-wide-glyphs.patch \
    file://0033-Disable-Faux-bolding-in-Qts-FreeType-FontEngine.patch \
    file://0034-Avoid-loading-comments-from-JPEG-and-PNG-files.patch \
"

SRC_URI_append_hardware = " \
    file://0035-eglfs-Support-multiple-device-integration.patch \
    file://0036-eglfs-Support-multiple-display.patch \
    file://0037-eglfs-Associate-keyboard-device-with-window.patch \
    file://0038-eglfs-Associate-touch-device-with-window.patch \
"

# Do not include Qt testability patch for release
SRC_URI_append = "${@'' if '${WEBOS_DISTRO_PRERELEASE}' == '' else ' file://0099-Move-testability-loading-code-to-the-QGuiApplication.patch'}"

# Needed to build libwayland_common_webos.a in qtwayland
WEBOS_NO_STATIC_LIBRARIES_WHITELIST = "libQt5PlatformSupport.a"

# Needed to build Qt5QmlDevTools in qtdeclarative
# | make[2]: *** No rule to make target `usr/lib/libQt5Bootstrap.a', needed by `../../lib/libQt5QmlDevTools.a'.  Stop.
WEBOS_NO_STATIC_LIBRARIES_WHITELIST += "libQt5Bootstrap.a"

VIRTUAL-RUNTIME_gpu-libs ?= ""
RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_gpu-libs}"

# Emulator eglfs support with NYX input subsystem
inherit webos_machine_impl_dep
DEPENDS_append_emulator = " nyx-lib"
SRC_URI_append_emulator = " file://0098-Emulator-NYX-integration-with-eglfs-plugin.patch"
PACKAGECONFIG_append_emulator = " kms eglfs"
EXTRA_QMAKEVARS_PRE_append_emulator = " INCLUDEPATH+=${STAGING_INCDIR}/drm EGLFS_EMULATOR_SUPPORT=QEMU"
