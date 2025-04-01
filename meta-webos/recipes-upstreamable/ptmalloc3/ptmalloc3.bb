# Copyright (c) 2012-2025 LG Electronics, Inc.

SECTION = "libs"
DESCRIPTION = "Multi-thread malloc implementation"
HOMEPAGE = "http://www.malloc.de/en/"
LICENSE = "LGPL-2.0-only & PD"
LIC_FILES_CHKSUM = " \
    file://COPYRIGHT;md5=5c8ad593874e48b27ded5334b58f1e0c \
    file://README;beginline=25;endline=30;md5=8bf336b6ed939dc95c1c22ebc255a082 \
"

PR = "r6"

SRC_URI = "http://www.malloc.de/malloc/ptmalloc3-current.tar.gz \
    file://ptmalloc3-current-webos.patch \
"

SRC_URI[md5sum] = "c0b9dd5f16f8eae979166dc74b60015c"
SRC_URI[sha256sum] = "f353606f24a579597a1ff5b51009a45d75da047b3975d82c3f613f85bcf312db"

S = "${WORKDIR}/${BPN}"

do_compile () {
    # Add options to enable full RELRO
    sed -i "s/^\(LDFLAGS := .*\)/\1 -Wl,-z,relro,-z,now/" Makefile.palm

    oe_runmake -f Makefile.palm CC="$CC"
}

do_install() {
    #oenote instaling ptmalloc3
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -m 555 -p ${S}/libptmalloc3.so    ${D}${libdir}
    install -m 666 ${S}/mmap_dev_heap.h ${D}/${includedir}
    install -m 666 ${S}/malloc-2.8.3.h  ${D}/${includedir}
    install -m 666 ${S}/malloc_utils.h  ${D}/${includedir}
    cp -R --no-dereference --preserve=mode,links -v ${S}/sysdeps         ${D}/${includedir}
    rm ${D}/${includedir}/sysdeps/generic/atomic.h
}

FILES:${PN} = " ${libdir}/lib*.so"
FILES:${PN}-dev = "${includedir}"
