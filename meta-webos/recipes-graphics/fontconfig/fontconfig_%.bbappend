# Copyright (c) 2013-2025 LG Electronics, Inc.

AUTHOR = "Bhooshan Supe <bhooshan.supe@lge.com>"
EXTENDPRAUTO:append = "webos1"

do_install:append() {
    # remove the links to the config files from the fontconfig
    # component that conflict with the webOS config files
    rm -f ${D}${sysconfdir}/fonts/conf.d/*.conf
}
