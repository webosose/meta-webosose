# Copyright (c) 2023 LG Electronics, Inc.

# Backport a fix which will be probably merged in meta-oe nanbield
# meta-webos-backports/meta-webos-backports-4.3/recipes-multimedia/openh264/openh264_%.bbappend
do_install() {
    oe_runmake install DESTDIR=${D} PREFIX=${prefix} LIBDIR_NAME=${baselib} SHAREDLIB_DIR=${libdir}
}
