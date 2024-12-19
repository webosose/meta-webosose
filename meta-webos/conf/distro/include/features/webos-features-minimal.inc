# Copyright (c) 2012-2024 LG Electronics, Inc.

require conf/distro/include/features/webos-features-headless.inc

# Increment when features are changed
_pr_suffix .= ".6"

# Addition/removal distro features
WEBOS_DISTRO_FEATURES_DEFAULT += "alsa"
WEBOS_DISTRO_FEATURES_OPENGL += "opengl"
WEBOS_DISTRO_FEATURES_WINDOW_SYSTEM += "wayland"
WEBOS_DISTRO_FEATURES_GRAPHICS_BACKEND = "graphics-drm"

# You can specify extra webos distro features.
WEBOS_DISTRO_FEATURES_EXTENDED += "webos-graphics webos-touch"

# Addition/removal image features
IMAGE_FEATURES += "${@ 'webos-minimal' if bb.data.inherits_class('webos_image', d) else ''}"
