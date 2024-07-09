# Copyright (c) 2012-2024 LG Electronics, Inc.

require conf/distro/include/features/webos-features-headless.inc

# Increment when features are changed
_pr_suffix .= ".5"

# Addition/removal distro features
WEBOS_DISTRO_FEATURES_DEFAULT += "alsa"
WEBOS_DISTRO_FEATURES_OPENGL += "opengl"
WEBOS_DISTRO_FEATURES_WINDOW_SYSTEM += "wayland"

# You can specify extra webos distro features.
WEBOS_DISTRO_FEATURES_EXTENDED += "webos-graphics webos-qt webos-touch"

# Addition/removal image features
IMAGE_FEATURES += "webos-minimal"
