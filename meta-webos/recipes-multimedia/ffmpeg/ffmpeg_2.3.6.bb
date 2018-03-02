# Copyright (c) 2014-2016 LG Electronics, Inc.

SUMMARY = "FFmpeg Library"
DESCRIPTION = "FFmpeg is the leading multimedia framework, able to decode, encode, transcode, mux, demux, stream, filter and play pretty much anything that humans and machines have created. It supports the most obscure ancient formats up to the cutting edge. No matter if they were designed by some standards committee, the community or a corporation."
HOMEPAGE = "http://ffmpeg.org/"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING.LGPLv2.1;md5=bd7a443320af8c812e4c18d1b79df004"

PR = "r6"

PROVIDES = "libav"

SRC_URI = "http://ffmpeg.org/releases/${BP}.tar.bz2 \
           file://0001-avformat-avienc-Correct-possible-dereference-of-null.patch \
           file://0002-libavcodec-takdec-Check-for-possible-EINVAL-value.patch \
           file://0003-avcodec-mpegvideo-Check-pointer-when-allocation-fail.patch \
           file://CVE-2016-1897-CVE-2016-1898-forbid_all_protocols_except_https_and_file.patch"

SRC_URI[md5sum] = "9c12f5081f04ecee449d9a4c42ad850c"
SRC_URI[sha256sum] = "cf1be1c5c3973b8db16b6b6e8e63a042d414fb5d47d3801a196cbba21a0a624a"

inherit autotools pkgconfig

FFMPEG_CONF = ' \
    --prefix=${prefix} \
    --enable-cross-compile \
    --target-os=linux \
    --arch=${TARGET_ARCH} \
    --cc="${CC}" \
    --disable-asm \
    --disable-yasm \
    --disable-stripping \
    --disable-bzlib \
    --disable-zlib \
    --disable-avfilter \
    --disable-avdevice \
    --disable-swresample \
    --disable-programs \
    --disable-doc \
    --disable-logging \
    --disable-debug \
    --disable-pthreads \
    --disable-w32threads \
    --disable-os2threads \
    --enable-thumb \
    --enable-shared \
    --disable-decoder=msmpeg4v3 \
    '

do_configure () {
    export LD="${CC}"
    ${S}/configure ${FFMPEG_CONF}
}

INSANE_SKIP_${PN}="textrel"
