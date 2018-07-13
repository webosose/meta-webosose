# Copyright (c) 2018 LG Electronics, Inc.

require gstreamer1.0-webos-common.inc

EXTENDPRAUTO_append_rpi = "webosrpi1"

WEBOS_REPO_NAME_rpi = "gst-libav"

WEBOS_VERSION_rpi = "1.14.0-1_d30724abcc248fdfd9e22638aafb54e52c68242d"

SRC_URI_append_rpi = " \
    git://source.ffmpeg.org/ffmpeg;destsuffix=git/gst-libs/ext/libav;name=ffmpeg;branch=release/3.4 \
"

do_fetch[vardeps] += "SRCREV_ffmpeg"
SRCREV_ffmpeg_rpi = "a877ab75eb8faa2de33c9118053f44b0d4548f09"
