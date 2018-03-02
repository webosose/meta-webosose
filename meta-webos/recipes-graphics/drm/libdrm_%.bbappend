# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

DEPENDS_remove_class-native = "libpciaccess udev"
DEPENDS_remove_class-nativesdk = "libpciaccess udev"
DEPENDS_append = " util-macros"

PACKAGECONFIG_class-native = ""
PACKAGECONFIG_class-nativesdk = ""
PACKAGECONFIG ??= "intel"
PACKAGECONFIG[intel] = "--enable-intel,--disable-intel,libpciaccess"

BBCLASSEXTEND = "native nativesdk"
