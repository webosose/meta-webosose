# Copyright (c) 2020 LG Electronics, Inc.

require webruntime.inc

PROVIDES = "virtual/webruntime"

PR = "${INC_PR}.2"

LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=392a1bd783afbb4f57e038c7d1af5611"

WEBOS_VERSION = "79.0.3945.79-41_030fd1e5a1cce1460792778a4798c8f5adc278e0"

WEBOS_GIT_PARAM_BRANCH_V8 = "@chromium79"

WEBOS_REPO_NAME = "chromium79"
WEBOS_REPO_NAME_V8 = "chromium-v8"

WEBOS_VERSION_V8 = "7.9.317.31-chromium79.5_f187e079dfbaadb3d2a573bac25a4f4d09d21a93"
SRCREV_v8 = "e876fd0e28bd3bda5815394874183b7e6079d440"

# Since _remove is always applied LAST, we cannot implement
# GN_ARGS_remove = "ozone_platform_wayland_external=true" here
# but thanks gn it overrides former gn args values with later ones
# so we just add appropriate args to the end of its list:
PACKAGECONFIG[google_ozone_wayland] = "\
    ozone_platform_wayland=true ozone_platform_wayland_external=false,\
    ozone_platform_wayland=false ozone_platform_wayland_external=true"

PACKAGECONFIG[v8_lite] = "v8_enable_lite_mode=true,v8_enable_lite_mode=false"

GN_ARGS_append = " \
  libdir=\"${libdir}\"\
  includedir=\"${includedir}\"\
"

# TODO: qemux86 build fails
PACKAGECONFIG_remove_qemux86 = "gstreamer umediaserver neva-media neva-webrtc gav"
#END TODO

D = "${OUT_DIR}/${BUILD_TYPE}/image"
