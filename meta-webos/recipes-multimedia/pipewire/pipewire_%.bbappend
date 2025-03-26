# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# fails to build with: meson.build:361:0: ERROR: Dependency 'webrtc-audio-processing' not found, tried pkgconfig
# http://gecko.lge.com:8000/Errors/Details/588440
# this is because of newer version in ./meta-webos/recipes-multimedia/webrtc-audio-processing/webrtc-audio-processing_1.0.bb used by pulseaudio
PACKAGECONFIG:remove = "webrtc-echo-cancelling"
