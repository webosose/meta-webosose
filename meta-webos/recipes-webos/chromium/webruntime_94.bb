# Copyright (c) 2022 LG Electronics, Inc.
WEBRUNTIME_REPO_VERSION = "94"

require webruntime.inc
require webruntime-repo${REPO_VERSION}.inc

PROVIDES = "virtual/webruntime"

PR = "${INC_PR}.1"

PACKAGECONFIG[debug] = "symbol_level=2 optimize_for_size=true use_debug_fission=true,symbol_level=0"
PACKAGECONFIG[debug-blink] = "blink_symbol_level=2,blink_symbol_level=1"

PACKAGECONFIG[v8_lite] = "v8_enable_lite_mode=true,v8_enable_lite_mode=false"

GN_ARGS_append = " neva_dcheck_always_on=true"
GN_ARGS_append = " use_x11=false"
GN_ARGS_remove = "ozone_platform_wayland_external=true"
GN_ARGS_remove = "use_xkbcommon=true"
PACKAGECONFIG[google_ozone_wayland] = "import(\"//neva/gow.gn\")"
PACKAGECONFIG += "google_ozone_wayland"
PACKAGECONFIG[intel_ozone_wayland] = "import(\"//neva/iow.gn\")"
PACKAGECONFIG += "intel_ozone_wayland"

# TODO: get rid of this when (and if) we adopt GPU info collector patch
GN_ARGS_remove = "use_webos_gpu_info_collector=true"
# TODO: get rid of this when we adopt system debugger patch
GN_ARGS_remove = "use_system_debugger_abort=true"

GN_ARGS_append = " \
  libdir=\"${libdir}\"\
  includedir=\"${includedir}\"\
  enable_mojom_closure_compile=false\
  enable_js_type_check=false\
  system_wayland_scanner_path=\"${RECIPE_SYSROOT_NATIVE}/usr/bin/wayland-scanner\" \
"

# TODO: qemux build fails
PACKAGECONFIG_remove_qemux86 = "gstreamer umediaserver neva-media gav"
PACKAGECONFIG_remove_qemux86-64 = "gstreamer umediaserver neva-media gav"
#END TODO

