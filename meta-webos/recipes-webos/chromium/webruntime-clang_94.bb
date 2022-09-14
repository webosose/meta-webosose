# Copyright (c) 2023 LG Electronics, Inc.

require webruntime_94.bb

PROVIDES = "virtual/webruntime"

PR = "r0"

inherit clang_libc

GN_ARGS:remove = "is_clang=false"
GN_ARGS += "is_clang=true"

GN_ARGS:remove = "use_custom_libcxx=false"
GN_ARGS += "use_custom_libcxx=true"

GN_ARGS += "target_sysroot=\"${STAGING_DIR_TARGET}\""

PACKAGECONFIG[umediaserver] = ",,umediaserver-clang"
PACKAGECONFIG[gstreamer] = "use_gst_media=true enable_webm_video_codecs=false,use_gst_media=false,g-media-pipeline-clang"
PACKAGECONFIG[neva-webrtc] = "use_neva_webrtc=true,use_neva_webrtc=false,media-codec-interface-clang"
PACKAGECONFIG[webos-codec] = "use_webos_codec=true,use_webos_codec=false,media-codec-interface-clang"
