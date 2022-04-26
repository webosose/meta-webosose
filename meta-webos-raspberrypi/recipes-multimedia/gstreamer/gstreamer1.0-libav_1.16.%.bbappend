# Copyright (c) 2018-2022 LG Electronics, Inc.

require gstreamer1.0-webos-common.inc

EXTENDPRAUTO:append:rpi = "webosrpi1"

WEBOS_REPO_NAME:rpi = "gst-libav"

#WEBOS_VERSION:rpi = "1.14.4-2_6910361bc5b2fd4b98bd3af8f1674918d755f60c"

#SRC_URI:append:rpi = " \
#    git://source.ffmpeg.org/ffmpeg;destsuffix=git/gst-libs/ext/libav;name=ffmpeg;branch=release/3.4 \
#"

#do_fetch[vardeps] += "SRCREV_ffmpeg"
#SRCREV_ffmpeg:rpi = "a877ab75eb8faa2de33c9118053f44b0d4548f09"
