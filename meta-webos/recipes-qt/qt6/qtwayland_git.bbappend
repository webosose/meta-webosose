# Copyright (c) 2013-2021 LG Electronics, Inc.

inherit webos_qt_global

EXTENDPRAUTO_append = "webos24"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append = " \
    file://0001-Add-option-for-more-features.patch \
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
# options available by 0001-Add-option-to-disable-plugins.patch
PACKAGECONFIG[eglstream-controller] = "-DFEATURE_wayland_eglstream_controller=ON,-DFEATURE_wayland_eglstream_controller=OFF,"
PACKAGECONFIG[client-hwintegration-plugins] = "-DFEATURE_wayland_client_hwintegration_plugins=ON,-DFEATURE_wayland_client_hwintegration_plugins=OFF,"
PACKAGECONFIG[platform-plugins] = "-DFEATURE_wayland_platform_plugins=ON,-DFEATURE_wayland_platform_plugins=OFF,"
PACKAGECONFIG[decoration-plugins] = "-DFEATURE_wayland_decoration_plugins=ON,-DFEATURE_wayland_decoration_plugins=OFF,"

# PACKAGECONFIG for webos
PACKAGECONFIG = "wayland-server wayland-client client-wl-shell"

# qtwayland-qmlplugins is not used in webos
RRECOMMENDS_${PN}_remove = "${PN}-qmlplugins"
