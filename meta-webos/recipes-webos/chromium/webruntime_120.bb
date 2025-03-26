# Copyright (c) 2024-2025 LG Electronics, Inc.

WEBRUNTIME_REPO_VERSION = "120"

require webruntime.inc
require webruntime-repo${REPO_VERSION}.inc

PROVIDES = "virtual/webruntime"

PR = "${INC_PR}.0"

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
    system_wayland_scanner_path=\"${STAGING_BINDIR_NATIVE}/wayland-scanner\" \
"

# Disable Advanced Encryption Standard features set for ARM in zlib component
GN_ARGS_AES = "disable_zlib_arm_aes_cflag=true"
GN_ARGS += "${GN_ARGS_AES}"

# Raspberry Pi does not support PMULL.
GN_ARGS_AES:raspberrypi4-64 = ""
GN_ARGS_NEON:raspberrypi4-64 = "arm_use_neon=false"
GN_ARGS_AES:raspberrypi3-64 = ""
GN_ARGS_NEON:raspberrypi3-64 = "arm_use_neon=false"
