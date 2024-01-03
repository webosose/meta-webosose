# Copyright (c) 2013-2023 LG Electronics, Inc.

inherit webos_qt_global

EXTENDPRAUTO:append = "webos43"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

PATCHTOOL = "git"

# Upstream-Status: Backport
SRC_URI:append = " \
    file://0001-Support-presentation-time-protocol.patch;maxver=6.2.* \
    file://0002-Use-scope-resolution-operator-for-request.patch;maxver=6.2.* \
    file://0003-Fix-to-have-presentation-feedback-sequence-timely.patch;maxver=6.3.0 \
    file://0004-Fix-Access-nullptr-returned-by-QWaylandSurface-clien.patch;maxver=6.3.0 \
"

# Upstream-Status: Inappropriate
SRC_URI:append = " \
    file://0005-Hotfix-default-seat-grabs-keyboardFocus-from-non-def.patch;minver=6.6.0 \
"

# More options for fine-tuned configuration
PACKAGECONFIG[brcm] = "-DFEATURE_wayland_brcm=ON,-DFEATURE_wayland_brcm=OFF,"
PACKAGECONFIG[drm-egl-server-buffer] = "-DFEATURE_wayland_drm_egl_server_buffer=ON,-DFEATURE_wayland_drm_egl_server_buffer=OFF,"
PACKAGECONFIG[libhybris-egl-server-buffer] = "-DFEATURE_wayland_libhybris_egl_server_buffer=ON,-DFEATURE_wayland_libhybris_egl_server_buffer=OFF,"
PACKAGECONFIG[shm-emulation-server-buffer] = "-DFEATURE_wayland_shm_emulation_server_buffer=ON,-DFEATURE_wayland_shm_emulation_server_buffer=OFF,"
PACKAGECONFIG[vulkan-server-buffer] = "-DFEATURE_wayland_vulkan_server_buffer=ON,-DFEATURE_wayland_vulkan_server_buffer=OFF,"
PACKAGECONFIG[client-fullscreen-shell-v1] = "-DFEATURE_wayland_client_fullscreen_shell_v1=ON,-DFEATURE_wayland_client_fullscreen_shell_v1=OFF,"
PACKAGECONFIG[client-ivi-shell] = "-DFEATURE_wayland_client_ivi_shell=ON,-DFEATURE_wayland_client_ivi_shell=OFF,"
PACKAGECONFIG[client-wl-shell] = "-DFEATURE_wayland_client_wl_shell=ON,-DFEATURE_wayland_client_wl_shell=OFF,"
PACKAGECONFIG[client-xdg-shell] = "-DFEATURE_wayland_client_xdg_shell=ON,-DFEATURE_wayland_client_xdg_shell=OFF,"

# Dropped in the upstream recipe since 6.5.1
PACKAGECONFIG[dmabuf-client-buffer] = "-DFEATURE_wayland_dmabuf_client_buffer=ON,-DFEATURE_wayland_dmabuf_client_buffer=OFF,libdrm"
PACKAGECONFIG[dmabuf-server-buffer] = "-DFEATURE_wayland_dmabuf_server_buffer=ON,-DFEATURE_wayland_dmabuf_server_buffer=OFF,libdrm"

# PACKAGECONFIG for webos
PACKAGECONFIG = "wayland-server wayland-client client-wl-shell dmabuf-client-buffer dmabuf-server-buffer drm-egl-server-buffer"

# qtwayland-qmlplugins is not used in webos
RRECOMMENDS:${PN}:remove = "${PN}-qmlplugins"

# Set QT_SKIP_AUTO_PLUGIN_INCLUSION as otherwise
# QtModulePluginTargets.cmake would complain during
# do_install_ptest_base about missing files that are deleted
# deliberately in do_install:append below.
# See https://codereview.qt-project.org/c/qt/qtbase/+/420212.
EXTRA_OECMAKE:append = " -DQT_SKIP_AUTO_PLUGIN_INCLUSION=ON"

do_install:append() {
    # Remove files unnecessary or conflict with qtwayland-webos
    rm -rf ${D}${QT6_INSTALL_PLUGINSDIR}/platforms \
        ${D}${QT6_INSTALL_PLUGINSDIR}/{wayland-decoration-client,wayland-graphics-integration-client} \
        ${D}${QT6_INSTALL_PLUGINSDIR}/wayland-graphics-integration-server/libqt-wayland-compositor-wayland-eglstream-controller.so
}
