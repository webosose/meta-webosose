# Copyright (c) 2013-2021 LG Electronics, Inc.

inherit webos_qt_global

EXTENDPRAUTO_append = "webos26"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

# Apply webOS specific patches to only SRCREV tested
WEBOS_PATCH_SRCREV = "e9ac64ab5e5364ae4d309227422588417463efdd"
PATCHTOOL = "git"

# Upstream-Status: Backport
SRC_URI_append = " \
    file://0001-Support-presentation-time-protocol.patch;rev=${WEBOS_PATCH_SRCREV} \
    file://0002-Use-scope-resolution-operator-for-request.patch;rev=${WEBOS_PATCH_SRCREV} \
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

# PACKAGECONFIG for webos
PACKAGECONFIG = "wayland-server wayland-client client-wl-shell"

# qtwayland-qmlplugins is not used in webos
RRECOMMENDS_${PN}_remove = "${PN}-qmlplugins"

do_install_append() {
    # Remove files unnecessary or conflict with qtwayland-webos
    rm -rf ${D}${QT6_INSTALL_PLUGINSDIR}/platforms \
        ${D}${QT6_INSTALL_PLUGINSDIR}/{wayland-decoration-client,wayland-graphics-integration-client} \
        ${D}${QT6_INSTALL_PLUGINSDIR}/wayland-graphics-integration-server/libqt-wayland-compositor-wayland-eglstream-controller.so
}
