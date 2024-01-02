# Copyright (c) 2012-2024 LG Electronics, Inc.

require conf/distro/include/features/webos-features-core-boot.inc

# Increament when features are changed
_pr_suffix .= ".1"

# Addition/removal distro features
WEBOS_DISTRO_FEATURES_DEFAULT += "alsa ext2 irda largefile xattr"
WEBOS_DISTRO_FEATURES_WINDOW_SYSTEM += "wayland"
WEBOS_DISTRO_FEATURES_USB += "usbhost usbgadget"
WEBOS_DISTRO_FEATURES_CONNECTIVITY += "bluetooth ipv6"
WEBOS_DISTRO_FEATURES_NETWORKING += "nfs zeroconf"

# You can specify extra webos distro features.
WEBOS_DISTRO_FEATURES_EXTENDED = "webos-touch"
DISTRO_FEATURES:append = " ${WEBOS_DISTRO_FEATURES_EXTENDED}"

# Addition/removal preferred provider or version
VIRTUAL-RUNTIME_initscripts = "webos-initscripts"
PREFERRED_RPROVIDER_initd-functions = "webos-initscripts"

# Addition/removal image features
IMAGE_FEATURES += "webos-minimal webos-extract-ls2-api webos-validate-ls2-conf"
