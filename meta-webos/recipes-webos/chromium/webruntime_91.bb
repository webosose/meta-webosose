# Copyright (c) 2021 LG Electronics, Inc.
WEBRUNTIME_REPO_VERSION = "91"

require webruntime.inc
require webruntime-repo${REPO_VERSION}.inc

PROVIDES = "virtual/webruntime"

PR = "${INC_PR}.0"

PACKAGECONFIG[debug] = "symbol_level=2 optimize_for_size=true use_debug_fission=true,symbol_level=0"
PACKAGECONFIG[debug-blink] = "blink_symbol_level=2,blink_symbol_level=1"

PACKAGECONFIG[v8_lite] = "v8_enable_lite_mode=true,v8_enable_lite_mode=false"

# FIXME(neva): LTTng impl is incompatible with Perfetto feature in Chromium v.91
# https://chromium-review.googlesource.com/c/chromium/src/+/2632755
PACKAGECONFIG[lttng] = "use_lttng=false,,"

PACKAGECONFIG_remove="jumbo"

GN_ARGS_append = " use_x11=false"
GN_ARGS_remove = "ozone_platform_wayland_external=true"
GN_ARGS_remove = "use_xkbcommon=true"
PACKAGECONFIG[google_ozone_wayland] = "import(\"//neva/gow.gn\")"
PACKAGECONFIG[intel_ozone_wayland] = "import(\"//neva/iow.gn\")"
PACKAGECONFIG += "intel_ozone_wayland"

# TODO: get rid of this when it will be removed on RP side from
# meta-lg-webos/meta-webos/recipes-webos/chromium/webruntime.inc
GN_ARGS_remove = "linux_use_bundled_binutils=false"

# TODO: get rid of this when (and if) we adopt GPU info collector patch
GN_ARGS_remove = "use_webos_gpu_info_collector=true"
# TODO: get rid of this when we adopt system debugger patch
GN_ARGS_remove = "use_system_debugger_abort=true"

GN_ARGS_append = " \
  libdir=\"${libdir}\"\
  includedir=\"${includedir}\"\
  enable_mojom_closure_compile=false\
  enable_js_type_check=false\
"

# TODO: qemux86 build fails
PACKAGECONFIG_remove_qemux86 = "gstreamer umediaserver neva-media gav"
#END TODO

