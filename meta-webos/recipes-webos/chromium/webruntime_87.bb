# Copyright (c) 2021 LG Electronics, Inc.
WEBRUNTIME_REPO_VERSION = "87"

require webruntime.inc
require webruntime-repo${REPO_VERSION}.inc

PROVIDES = "virtual/webruntime"

PR = "${INC_PR}.0"

GN_ARGS_append = " use_x11=false"

PACKAGECONFIG[debug] = "symbol_level=2,symbol_level=0"
PACKAGECONFIG[debug-blink] = "blink_symbol_level=1,blink_symbol_level=0"

# Since _remove is always applied LAST, we cannot implement
# GN_ARGS_remove = "ozone_platform_wayland_external=true" here
# but thanks gn it overrides former gn args values with later ones
# so we just add appropriate args to the end of its list:
PACKAGECONFIG[google_ozone_wayland] = "\
   ozone_platform_wayland=true ozone_platform_wayland_external=false \
   use_system_libwayland=true use_wayland_gbm=false use_system_minigbm=false,\
   ozone_platform_wayland=false ozone_platform_wayland_external=true"

PACKAGECONFIG[v8_lite] = "v8_enable_lite_mode=true,v8_enable_lite_mode=false"

PACKAGECONFIG_remove="jumbo"

GN_ARGS_append = " \
  libdir=\"${libdir}\"\
  includedir=\"${includedir}\"\
  enable_mojom_closure_compile=false\
  enable_js_type_check=false\
  use_local_storage_tracker=true\
"
