# Copyright (c) 2023 LG Electronics, Inc.

require webruntime_94.bb

PROVIDES = "virtual/webruntime"

PR = "r2"

inherit clang_libc

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', '', 'system-libcxx', d)}"
PACKAGECONFIG[system-libcxx] = ",,llvm-native clang"

GN_ARGS:remove = "is_clang=false"
GN_ARGS += "is_clang=true"

GN_ARGS += "target_sysroot=\"${STAGING_DIR_TARGET}\""

GN_ARGS:remove = "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'use_custom_libcxx=false', 'use_custom_libcxx=true', d)}"

GN_ARGS += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'use_custom_libcxx=true', 'use_custom_libcxx=false', d)}"
GN_ARGS += " ${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', '', 'clang_extra_cxxflags=\\\"-I${STAGING_DIR_TARGET}/usr/include/c++/v1\\\"', d)}"

GN_ARGS += "webos_rpath=\"${libdir}/cbe\""

PACKAGECONFIG[umediaserver] = ",,umediaserver-clang"
PACKAGECONFIG[gstreamer] = "use_gst_media=true enable_webm_video_codecs=false,use_gst_media=false,g-media-pipeline-clang"
PACKAGECONFIG[neva-webrtc] = "use_neva_webrtc=true,use_neva_webrtc=false,media-codec-interface-clang"
PACKAGECONFIG[webos-codec] = "use_webos_codec=true,use_webos_codec=false,media-codec-interface-clang"
