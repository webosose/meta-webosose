# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

DEPENDS_remove_class-nativesdk = "libpciaccess udev"
DEPENDS_append = " util-macros"

PACKAGECONFIG_class-nativesdk = ""
PACKAGECONFIG ??= "intel"
PACKAGECONFIG[intel] = "--enable-intel,--disable-intel,libpciaccess"

BBCLASSEXTEND = "native nativesdk"
