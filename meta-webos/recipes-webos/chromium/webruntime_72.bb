# Copyright (c) 2019 LG Electronics, Inc.

require webruntime.inc

PROVIDES = "virtual/webruntime"

PR = "${INC_PR}.1"

WEBOS_GIT_PARAM_BRANCH_V8 = "@chromium72"

WEBOS_VERSION = "72.0.3626.121-20_fd67dafc6d1bea9e4acdffe23b26fa1735b4d797"
WEBOS_REPO_NAME = "chromium72"
WEBOS_REPO_NAME_V8 = "chromium-v8"

WEBOS_VERSION_V8 = "7.2.502.28-chromium72.7_cd41b2480f1e1c640887d70cd4435bae2ed55165"
SRCREV_v8 = "3c24ac757b6083171b208457e5ff7b2a15e5863e"

# In Neva we don't need the toolchain hack as we are allowing to use cros toolchain
# directly, and we also have changes to pass host extra flags
deltask write_toolchain_file
GN_ARGS_remove = "host_toolchain=\"//build/toolchain/yocto:clang_yocto_native\""
GN_ARGS_append = " host_pkg_config=\"pkg-config-native\""

# TODO: get rid of this when (and if) we adopt GPU info collector patch
GN_ARGS_remove = "use_webos_gpu_info_collector=true"

# Disable closure_compile
# Else we need HOSTTOOLS += "java"
GN_ARGS_append = " closure_compile=false"

CBE_DATA_LOCALES_PATH = "${CBE_DATA_PATH}/neva_locales"
LTTNG_PROVIDER = "liblttng_provider.so"
