# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

EXTRA_OECONF_append_class-native = " --enable-glx"

REQUIRED_DISTRO_FEATURES_class-native = ""
REQUIRED_DISTRO_FEATURES_class-nativesdk = ""

PACKAGECONFIG_class-native = "egl"
PACKAGECONFIG_class-nativesdk = "egl"

BBCLASSEXTEND = "native nativesdk"
