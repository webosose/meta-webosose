# Copyright (c) 2020 LG Electronics, Inc.

require webruntime.inc

PROVIDES = "virtual/webruntime"

PR = "${INC_PR}.1"

WEBOS_VERSION = "79.0.3945.79-23_bf8a39e38b406d78f7695ab73d7a1c4c674733ed"

WEBOS_GIT_PARAM_BRANCH_V8 = "@chromium79"

WEBOS_REPO_NAME = "chromium79"
WEBOS_REPO_NAME_V8 = "chromium-v8"

WEBOS_VERSION_V8 = "7.9.317.31-chromium79.4_10ebaf4a53cc187953a90a50f90bbbed85e859ff"
SRCREV_v8 = "9d74e94dcf33ad1b4c70d16ab499f844e0893f60"

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
PACKAGECONFIG_remove_qemux86 = "gstreamer umediaserver neva-media gav"
#END TODO

D = "${OUT_DIR}/${BUILD_TYPE}/image"
