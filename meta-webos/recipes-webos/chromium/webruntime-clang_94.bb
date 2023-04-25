# Copyright (c) 2023 LG Electronics, Inc.

require webruntime_94.bb

PROVIDES = "virtual/webruntime"

PR = "r4"

inherit clang_libc

GCC_CROSS_VER = "11.3.0"
DEPEXT = "${@bb.utils.contains('WEBRUNTIME_CLANG_STDLIB', '1', '', '-clang', d)}"

PACKAGECONFIG += "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', '', 'system-libcxx', d)}"
PACKAGECONFIG[system-libcxx] = ",,llvm-native clang"

GN_ARGS_CLANG = "is_clang=true"

GN_ARGS += "target_sysroot=\"${STAGING_DIR_TARGET}\""

INCLUDE_PATH_STDLIB = " \
    -I${STAGING_INCDIR}/c++/${GCC_CROSS_VER} \
    -I${STAGING_INCDIR}/c++/${GCC_CROSS_VER}/${TARGET_SYS} \
"

INCLUDE_PATH_LIBCXX_EXT = " \
    -I${STAGING_INCDIR}/c++/v1 \
"

INCLUDE_PATH_LIBCXX = "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', '', '${INCLUDE_PATH_LIBCXX_EXT}', d)}"
INCLUDE_PATH_LIBCXX += " \
    -I${STAGING_INCDIR}/cbe \
    -I${STAGING_INCDIR}/cbe/gmp \
    -I${STAGING_INCDIR}/media-resource-calculator-clang \
"

# tcmalloc build is broken with clang++ and -mthumb as shown in:
# http://gecko.lge.com:8000/Errors/Details/528000
# http://gecko.lge.com:8000/Errors/Details/527999
ARM_INSTRUCTION_SET = "arm"

GN_ARGS += "${@bb.utils.contains('WEBRUNTIME_CLANG_STDLIB', '1', 'clang_use_stdlib=true clang_extra_cxxflags=\\\"${INCLUDE_PATH_STDLIB} ${TARGET_CC_ARCH}\\\"', 'clang_use_stdlib=false clang_extra_cxxflags=\\\"${INCLUDE_PATH_LIBCXX} ${TARGET_CC_ARCH}\\\"', d)}"

GN_ARGS += "webos_rpath=\"${libdir}/cbe\""

PACKAGECONFIG[umediaserver] = ",,umediaserver${DEPEXT}"
PACKAGECONFIG[gstreamer] = "use_gst_media=true enable_webm_video_codecs=false,use_gst_media=false,g-media-pipeline${DEPEXT}"
PACKAGECONFIG[neva-webrtc] = "use_neva_webrtc=true,use_neva_webrtc=false,media-codec-interface${DEPEXT}"
PACKAGECONFIG[webos-codec] = "use_webos_codec=true,use_webos_codec=false,media-codec-interface${DEPEXT}"

do_configure:prepend() {
    [ -f ${STAGING_DATADIR}/pkgconfig/umedia_api_clang.pc ] && \
    mv -n ${STAGING_DATADIR}/pkgconfig/umedia_api_clang.pc ${STAGING_DATADIR}/pkgconfig/umedia_api.pc
    [ -f ${STAGING_DATADIR}/pkgconfig/gmp-player-client-clang.pc ] && \
    mv -n ${STAGING_DATADIR}/pkgconfig/gmp-player-client-clang.pc ${STAGING_DATADIR}/pkgconfig/gmp-player-client.pc
}

