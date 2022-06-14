# Copyright (c) 2012-2022 LG Electronics, Inc.

PREFERRED_VERSION_webruntime = "91.%"
PREFERRED_VERSION_mksnapshot-cross-${TARGET_ARCH} = "91.%"
GOVERSION = "1.16.%"
PREFERRED_VERSION_pulseaudio = "15.0-%"

# Prefer gstreamer version 1.16.% for webos (instead of 1.18 from meta-webos-backports-3.3)
GSTREAMER_VERSION = "1.16.%"

PREFERRED_VERSION_gstreamer1.0              = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-plugins-bad  = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-plugins-base = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-plugins-cool = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-plugins-good = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-plugins-ugly = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-libav        = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-omx          = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-python       = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-rtsp-server  = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gstreamer1.0-vaapi        = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gst-devtools              = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gst-examples              = "${GSTREAMER_VERSION}"
PREFERRED_VERSION_gst-validate1.0           = "${GSTREAMER_VERSION}"
