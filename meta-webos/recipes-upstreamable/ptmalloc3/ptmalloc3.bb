# Copyright (c) 2012-2018 LG Electronics, Inc.

SECTION = "libs"
DESCRIPTION = "Multi-thread malloc implementation"
HOMEPAGE = "http://www.malloc.de/en/"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD;md5=3775480a712fc46a69647678acb234cb"

PR = "r4"

SRC_URI = "http://www.malloc.de/malloc/ptmalloc3-current.tar.gz \
           file://ptmalloc3-current-webos.patch "

SRC_URI[md5sum] = "c0b9dd5f16f8eae979166dc74b60015c"
SRC_URI[sha256sum] = "f353606f24a579597a1ff5b51009a45d75da047b3975d82c3f613f85bcf312db"

S = "${WORKDIR}/${BPN}"

do_compile () {
    make -f Makefile.palm CC="$CC"
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

FILES_${PN} = " ${libdir}/lib*.so"
FILES_${PN}-dev = "${includedir}"
