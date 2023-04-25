# Copyright (c) 2022-2023 LG Electronics, Inc.

require webruntime.inc
require webruntime-repo${REPO_VERSION}.inc

PROVIDES = "virtual/webruntime"

PR = "${INC_PR}.2"

PACKAGECONFIG[debug] = "symbol_level=2 optimize_for_size=true use_debug_fission=true,symbol_level=0"
PACKAGECONFIG[debug-blink] = "blink_symbol_level=2,blink_symbol_level=1"

PACKAGECONFIG[v8_lite] = "v8_enable_lite_mode=true,v8_enable_lite_mode=false"

GN_ARGS:append = " neva_dcheck_always_on=true"
GN_ARGS:append = " use_x11=false"
PACKAGECONFIG[google_ozone_wayland] = "import(\"//neva/gow.gn\")"
PACKAGECONFIG += "google_ozone_wayland"
PACKAGECONFIG[intel_ozone_wayland] = "import(\"//neva/iow.gn\")"
PACKAGECONFIG += "intel_ozone_wayland"

GN_ARGS:append = " \
  libdir=\"${libdir}\"\
  includedir=\"${includedir}\"\
  enable_mojom_closure_compile=false\
  enable_js_type_check=false\
  use_neva_media_player_camera=true\
  system_wayland_scanner_path=\"${RECIPE_SYSROOT_NATIVE}/usr/bin/wayland-scanner\" \
"
