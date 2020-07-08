# Copyright (c) 2013-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos80"

# Remove LGPL3-only files
python do_patch_append() {
    bb.build.exec_func('remove_LGPL3', d)
}

remove_LGPL3() {
    rm -vf ${S}/src/plugins/platforms/andr*oid/extract.cpp
}

# Disable features we don't use in all webOS products
PACKAGECONFIG_DEFAULT_remove = "dbus"

# Enable accessibility for qtquickcontrols
PACKAGECONFIG_append = " accessibility"

# Disable widget features
PACKAGECONFIG_remove = "widgets"

# Configure qt5 to use platform harfbuzz
PACKAGECONFIG_append = " harfbuzz"
PACKAGECONFIG[harfbuzz] = "-system-harfbuzz,-qt-harfbuzz,harfbuzz"

# Configure qt5 to compile with GL ES2 instead of default desktop GL
PACKAGECONFIG_GL = "gles2"
# We have alsa in DISTRO_FEATURES so it was enabled before
#PACKAGECONFIG_append = " alsa"
# We had this enabled in our old gpro/meta-qt5 recipe
#PACKAGECONFIG_append = " iconv"
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
# No longer added automatically
PACKAGECONFIG_append = " gif"
# Needed since qtwayland 5.12
PACKAGECONFIG_append = " xkbcommon"

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
PACKAGECONFIG[lttng] = "-trace lttng,-trace no,lttng-ust"
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

# Emulator eglfs support with NYX input subsystem
inherit webos_machine_impl_dep
PACKAGECONFIG[webos-emulator] = "-webos-emulator,-no-webos-emulator,nyx-lib"
PACKAGECONFIG_append_emulator = " gbm kms eglfs webos-emulator"

# Patches from 5.12.meta-webos.28 based on 5.12.meta-qt5.3
SRC_URI_append = " \
    file://0001-Decrease-the-size-of-QFontCache.patch \
    file://0002-Fix-floating-point-clip-rectangle-rounding-in-OpenGL.patch \
    file://0003-Fix-allocateTimerId.patch \
    file://0004-LTTNG-tracing-support-in-QtGUI.patch \
    file://0005-Add-the-accessiblebridge-as-a-plugintype-of-gui-modu.patch \
    file://0006-Disable-Faux-bolding-in-Qts-FreeType-FontEngine.patch \
    file://0007-Avoid-loading-comments-from-JPEG-and-PNG-files.patch \
    file://0008-Keyboard-Mouse-eglfs-patch-for-Emulator.patch \
    file://0009-Modify-the-touch-event-for-emulator.patch \
    file://0010-eglfs-kms-Choose-unique-primary-planes-for-each-crtc.patch \
    file://0011-Allow-word-break-wrapping-in-Korean-text.patch \
"

SRC_URI_append_hardware = " \
    file://0012-eglfs-Support-multiple-device-integration.patch \
    file://0013-eglfs-Support-multiple-display.patch \
    file://0014-eglfs-Associate-keyboard-touch-device-with-window.patch \
"

VIRTUAL-RUNTIME_gpu-libs ?= ""
RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_gpu-libs}"
