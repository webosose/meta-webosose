# Copyright (c) 2023 LG Electronics, Inc.

require conf/distro/include/webos-features-minimal.inc

# Addition/removal distro features

# You can specify the packagegroups to be installed using the following options:
# webos-<feature>: If this feature is specified, packagegroup-webos-<feature> will be installed.
WEBOS_DISTRO_FEATURES_EXTENDED += "\
    webos-audio \
    webos-camera \
    webos-connectivity \
    webos-diagnostics \
    webos-graphics \
    webos-i18n \
    webos-media \
    webos-media-drm \
"

NO_RECOMMENDATIONS = "0"
