# Copyright (c) 2018 LG Electronics, Inc.

require gstreamer1.0-webos-common.inc

EXTENDPRAUTO_append_rpi = "webosrpi1"

# gles2 make build error , so override below and remove gles2.
# original: PACKAGECONFIG_GL ?= "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl', '', d)}"
PACKAGECONFIG_GL_rpi = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', ' egl', '', d)}"

WEBOS_REPO_NAME_rpi = "gst-plugins-base"

WEBOS_VERSION_rpi = "1.14.0-1_5e2338e701af08c9493f32426a5f712ae973ebbf"
