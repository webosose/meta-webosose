# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

PACKAGECONFIG_class-native = "x11 opengl"
PACKAGECONFIG_class-nativesdk = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"
BBCLASSEXTEND = "native nativesdk"
