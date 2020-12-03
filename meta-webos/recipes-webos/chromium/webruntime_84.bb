# Copyright (c) 2020 LG Electronics, Inc.

require webruntime.inc

PROVIDES = "virtual/webruntime"

PR = "${INC_PR}.2"

LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=916569994c13b72c74223963ee65895d"

WEBOS_VERSION = "84.0.4147.89-5_a84eabfc35790500c19cb618434453b47bac70bd"

WEBOS_GIT_PARAM_BRANCH_V8 = "@chromium84"

WEBOS_REPO_NAME = "chromium84"
WEBOS_REPO_NAME_V8 = "chromium-v8"

WEBOS_VERSION_V8 = "8.4.371.19-chromium84.2_9ea4957fd61303416e2dc36235c5776240472d44"
SRCREV_v8 = "5c1d89dd2945a10cf7a6a3458050b3177a870b09"

# Since _remove is always applied LAST, we cannot implement
# GN_ARGS_remove = "ozone_platform_wayland_external=true" here
# but thanks gn it overrides former gn args values with later ones
# so we just add appropriate args to the end of its list:
PACKAGECONFIG[google_ozone_wayland] = "\
    ozone_platform_wayland=true ozone_platform_wayland_external=false,\
    ozone_platform_wayland=false ozone_platform_wayland_external=true"

PACKAGECONFIG[v8_lite] = "v8_enable_lite_mode=true,v8_enable_lite_mode=false"

PACKAGECONFIG_remove="jumbo"

GN_ARGS_append = " \
  use_system_minigbm=false \
    use_wayland_gbm=false \
"

GN_ARGS_append = " \
  libdir=\"${libdir}\"\
  includedir=\"${includedir}\"\
"

# TODO: qemux86 build fails
PACKAGECONFIG_remove_qemux86 = "gstreamer umediaserver neva-media gav neva-webrtc"
#END TODO

# Fix build error for OSE RPi4-64
SRC_URI_append_raspberrypi4-64 = "\
    file://0001-op-ds-build-Fix-build-error-when-target-is-arm64.patch \
"
