# Copyright (c) 2021-2022 LG Electronics, Inc.
WEBRUNTIME_REPO_VERSION = "91"

require webruntime.inc
require webruntime-repo${REPO_VERSION}.inc

PROVIDES = "virtual/webruntime"

PR = "${INC_PR}.4"

PACKAGECONFIG[debug] = "symbol_level=2 optimize_for_size=true use_debug_fission=true,symbol_level=0"
PACKAGECONFIG[debug-blink] = "blink_symbol_level=2,blink_symbol_level=1"

PACKAGECONFIG[v8_lite] = "v8_enable_lite_mode=true,v8_enable_lite_mode=false"

GN_ARGS_append = " use_x11=false"
PACKAGECONFIG[google_ozone_wayland] = "import(\"//neva/gow.gn\")"
PACKAGECONFIG[intel_ozone_wayland] = "import(\"//neva/iow.gn\")"
PACKAGECONFIG += "intel_ozone_wayland"

GN_ARGS_append = " \
  libdir=\"${libdir}\"\
  includedir=\"${includedir}\"\
  enable_mojom_closure_compile=false\
  enable_js_type_check=false\
  use_neva_media_player_camera=true\
"

# TODO: qemux build fails
PACKAGECONFIG_remove_qemux86 = "gstreamer umediaserver neva-media gav"
PACKAGECONFIG_remove_qemux86-64 = "gstreamer umediaserver neva-media gav"
#END TODO

TUNE_FEATURES_remove_raspberrypi4-64 = "crypto"
