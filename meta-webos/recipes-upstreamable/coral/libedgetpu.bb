DESCRIPTION = "Edge TPU runtime library(libedgetpu)"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

PV = "grouper"
SRCREV_libedgetpu = "3164995622300286ef2bb14d7fdc2792dae045b7"
SRCREV_tensorflow = "a5ed5f39b675a1c6f315e0caf3ad4b38478fa571"

SRC_URI = " \
    git://github.com/google-coral/libedgetpu.git;name=libedgetpu;branch="master";protocol=https \
    git://github.com/tensorflow/tensorflow.git;name=tensorflow;destsuffix=tensorflow;branch=r2.9;protocol=https \
    file://001-v2.9_libedgetpu_makefile.patch \
    file://001-v2.9_libedgetpu_allocated_buffer_header.patch \
    file://edgetpu.pc.in \
    file://edgetpu-max.pc.in \
"

S = "${WORKDIR}/git"
B = "${WORKDIR}/git/makefile_build"

DEPENDS = " \
    flatbuffers-native \
    vim-native \
    flatbuffers \
    abseil-cpp \
    libusb1 \
"

#EXTRA_OEMAKE = " libedgetpu"
CXXFLAGS += "-Wno-error=return-type"

do_compile:prepend() {

    CPU="k8"
    if [ ${TARGET_ARCH} = "aarch64" ]; then
        CPU="aarch64"
    elif [ ${TARGET_ARCH} = "arm" ]; then
        CPU="armv7a"
    fi

    echo "CPU=${CPU}"
    export TFROOT=${WORKDIR}/tensorflow
}

do_install:append() {
    # install libedgetpu1-std(throttled) and libedgetpu1-max(direct, max frequency)
    install -d ${D}/${libdir}
    install -m 0755 ${S}/out/throttled/k8/libedgetpu.so.1 ${D}/${libdir}
    cd ${D}/${libdir}
    ln -snf libedgetpu.so.1 libedgetpu.so
    cd -

    install -m 0755 ${S}/out/direct/k8/libedgetpu.so.1 ${D}/${libdir}/libedgetpu_max.so.1
    cd ${D}/${libdir}
    ln -snf libedgetpu_max.so.1 libedgetpu_max.so
    cd -

    install -d ${D}/etc/udev/rules.d/
    install -m 644 ${S}/debian/edgetpu-accelerator.rules ${D}/etc/udev/rules.d/99-edgetpu-accelerator.rules

    # install header file
    install -d ${D}/${includedir}
    install -m 755 ${S}/tflite/public/edgetpu.h ${D}/${includedir}

    # install pkgconfig file
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/edgetpu.pc.in ${D}${libdir}/pkgconfig/edgetpu.pc
    sed -i 's:@version@:${PV}:g
        s:@libdir@:${libdir}:g
        s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/edgetpu.pc

    install -m 0644 ${WORKDIR}/edgetpu-max.pc.in ${D}${libdir}/pkgconfig/edgetpu-max.pc
    sed -i 's:@version@:${PV}:g
        s:@libdir@:${libdir}:g
        s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/edgetpu-max.pc
}

FILES:${PN}-dev = ""

INSANE_SKIP:${PN} = "dev-so"

FILES:${PN} += "${libdir}/*.so*"
FILES:${PN}-dev += "${includedir}/* ${libdir}/pkgconfig/*.pc"


BBCLASSEXTEND = "native nativesdk"
