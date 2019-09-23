# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"
EXTRA_OEMAKE = "MAKEFLAGS= DESTDIR=${D} LIBDIR=${libdir}/crda LDLIBREG='-Wl,-rpath,${libdir}/crda -lreg' \
                UDEV_RULE_DIR=${base_libdir}/udev/rules.d/"
EXTRA_OEMAKE_append = " ${@oe.utils.conditional("DEFAULT_CRYPT", "openssl", "USE_OPENSSL=1", "", d)}"
