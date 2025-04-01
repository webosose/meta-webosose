# Copyright (c) 2018-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos5"

# Adds dependency on GPLv2 tbb
PACKAGECONFIG:remove = "tbb"

# Depends on blacklisted glog
PACKAGECONFIG:remove:armv4 = "eigen"
PACKAGECONFIG:remove:armv5 = "eigen"

# In case of wayland, wayland wayland-natve libxkbcommon are needed
WAYLAND_DEPENDENCY = "wayland wayland-native libxkbcommon"
PACKAGECONFIG[wayland] = "-DWITH_WAYLAND=ON,-DWITH_WAYLAND=OFF,${WAYLAND_DEPENDENCY},"

# Adds deep learning library for AI Framework
PACKAGECONFIG:append = "${@bb.utils.contains('DISTRO_FEATURES', 'webos-aiframework', ' opencl dnn text libav wayland', '', d)}"

# http://caprica.lgsvl.com:8080/Errors/Details/1447234
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-apps:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-apps:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

export CCACHE_MAXSIZE = "2G"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/894546
# ERROR: QA Issue: File /usr/src/debug/lib32-opencv/4.9.0/modules/dnn/layers/cpu_kernels/conv_block.neon.cpp in package lib32-opencv-src contains reference to TMPDIR
# File /usr/src/debug/lib32-opencv/4.9.0/modules/dnn/layers/cpu_kernels/fast_gemm_kernels.neon.cpp in package lib32-opencv-src contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
