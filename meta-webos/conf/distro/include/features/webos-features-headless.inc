# Copyright (c) 2024 LG Electronics, Inc.

require conf/distro/include/features/webos-features-core-boot.inc

# Increment when features are changed
_pr_suffix .= ".3"

# Addition/removal distro features
WEBOS_DISTRO_FEATURES_DEFAULT += "ext2 irda largefile xattr"
WEBOS_DISTRO_FEATURES_USB += "usbhost usbgadget"
WEBOS_DISTRO_FEATURES_CONNECTIVITY += "bluetooth ipv6"
WEBOS_DISTRO_FEATURES_NETWORKING += "nfs zeroconf"

# You can specify extra webos distro features.
WEBOS_DISTRO_FEATURES_EXTENDED = "webos-essential"
DISTRO_FEATURES:append = " ${WEBOS_DISTRO_FEATURES_EXTENDED}"

# This one is needed for native as well, use separate variable for that
WEBOS_DISTRO_FEATURES_QT ?= "webos-qt"

# Also have to add webos-qt to distro features native and nativesdk, if not
# | ERROR: Required build target 'nativesdk-packagegroup-sdk-host' has no buildable providers.
# | Missing or unbuildable dependency chain was: ['nativesdk-packagegroup-sdk-host', 'nativesdk-qtwayland-tools']
#
# | ERROR: Nothing PROVIDES 'qtwayland-native' (but qtwayland-webos.bb DEPENDS on or otherwise requires it)
# | qtwayland-native was skipped: missing required distro feature 'webos-qt' (not in DISTRO_FEATURES)
DISTRO_FEATURES:append = " ${WEBOS_DISTRO_FEATURES_QT}"
DISTRO_FEATURES_NATIVE:append = " ${WEBOS_DISTRO_FEATURES_QT}"
DISTRO_FEATURES_NATIVESDK:append = " ${WEBOS_DISTRO_FEATURES_QT}"

# Addition/removal preferred provider or version
VIRTUAL-RUNTIME_initscripts = "webos-initscripts"
PREFERRED_RPROVIDER_initd-functions = "webos-initscripts"

# Addition/removal image features
IMAGE_FEATURES += "${@ 'webos-extract-ls2-api webos-validate-ls2-conf' if bb.data.inherits_class('webos_image', d) else ''}"
