DESCRIPTION = "Edge TPU runtime library(libedgetpu)"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRCREV = "efb73cc94dac29dc590a243109d4654c223e008c"
SRC_URI = "git://github.com/google-coral/libedgetpu \
           file://libedgetpu_arm.so.1.0 \
           file://libedgetpu_aarch64.so.1.0 \
           file://libedgetpu_x86_64.so.1.0 \
           file://edgetpu-accelerator.rules \
           file://edgetpu.pc.in \
          "

S = "${WORKDIR}/git"

RDEPENDS:${PN} += " \
    libusb1 \
"

do_configure() {

    if [ ${TARGET_ARCH} = "aarch64" ]; then
        echo "use libedgetpu_aarch64.so.1.0"
        install -m 644 ${WORKDIR}/libedgetpu_aarch64.so.1.0 ${S}/libedgetpu.so.1.0
    elif [ ${TARGET_ARCH} = "arm" ]; then
        echo "use libedgetpu_arm.so.1.0"
        install -m 644 ${WORKDIR}/libedgetpu_arm.so.1.0 ${S}/libedgetpu.so.1.0
    elif [ ${TARGET_ARCH} = "x86_64" ]; then
        echo "use libedgetpu_x86_64.so.1.0"
        install -m 644 ${WORKDIR}/libedgetpu_x86_64.so.1.0 ${S}/libedgetpu.so.1.0
    fi

    install -m 644 ${WORKDIR}/edgetpu-accelerator.rules ${S}
}

do_install() {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${S}/edgetpu-accelerator.rules \
                    ${D}${sysconfdir}/udev/rules.d/99-edgetpu-accelerator.rules

    install -d ${D}/${libdir}
    install -m 755 ${S}/libedgetpu.so.1.0 \
                   ${D}/${libdir}/libedgetpu.so

    # install header file
    install -d ${D}/${includedir}
    install -m 755 ${S}/tflite/public/edgetpu.h ${D}/${includedir}

    # install pkgconfig file
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/edgetpu.pc.in ${D}${libdir}/pkgconfig/edgetpu.pc
    sed -i 's:@version@:${PV}:g
        s:@libdir@:${libdir}:g
        s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/edgetpu.pc

}

FILES:${PN}-dev = ""

INSANE_SKIP:${PN} += "dev-so \
                      already-stripped \
                      file-rdeps \
                     "

INSANE_SKIP:${PN}-dev += "dev-elf"
FILES:${PN} += "${libdir}/* ${includedir}/* ${sysconfdir}/*"
