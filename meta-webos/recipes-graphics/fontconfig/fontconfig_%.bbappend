# Copyright (c) 2013-2020 LG Electronics, Inc.

AUTHOR = "Bhooshan Supe <bhooshan.supe@lge.com>"
EXTENDPRAUTO_append = "webos1"

do_install_append() {
    # remove the links to the config files from the fontconfig
    # component that conflict with the webOS config files
    rm -f ${D}${sysconfdir}/fonts/conf.d/*.conf
}
