# Copyright (c) 2018-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# Do not install nspr in usr/include, but in usr/include/nspr, the
# preferred path upstream.
EXTRA_OECONF += "--includedir=${includedir}/nspr"
do_install_append() {
    sed -i  \
    -e 's:${includedir}:${includedir}/nspr:g' \
    -e 's:Cflags\::Cflags\: -I${includedir}/nspr:g' \
    ${D}${libdir}/pkgconfig/nspr.pc
}
