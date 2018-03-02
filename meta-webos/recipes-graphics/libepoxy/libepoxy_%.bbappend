# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

REQUIRED_DISTRO_FEATURES_class-native = ""
REQUIRED_DISTRO_FEATURES_class-nativesdk = ""

PACKAGECONFIG_class-native = "egl"
PACKAGECONFIG_class-nativesdk = "egl"

BBCLASSEXTEND = "native nativesdk"
