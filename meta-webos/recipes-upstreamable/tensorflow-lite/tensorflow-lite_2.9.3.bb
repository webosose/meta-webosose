# Copyright (c) 2022-2024 LG Electronics, Inc.

require tensorflow-lite_2.9.3.inc

SRC_URI += " \
    file://tensorflowlite.pc.in \
"

EXTRA_OECMAKE += "-DBUILD_SHARED_LIBS=ON"

AIF_INSTALL_DIR = "${datadir}/aif"

do_install() {
    # install libraries
    install -d ${D}/${libdir}
    install -m 0644 $(find . -name "*.so") ${D}${libdir}

    # armnn expects libtensorflowlite.so not libtensorflow-lite.sh:
    # $ grep libtensorflowlite /OE/lge/build/webos/dunfell/BUILD/work/qemux86_64-webos-linux/armnn/21.11-r1/git/delegate/cmake/Modules/FindTfLite.cmake
    # find_library(TfLite_LIB NAMES "libtensorflow_lite_all.so" "libtensorflowlite.so" HINTS ${TFLITE_LIB_ROOT} ${TFLITE_LIB_ROOT}/tensorflow/lite)
    ln -snf libtensorflow-lite.so ${D}/${libdir}/libtensorflowlite.so

    # install header files
    install -d ${D}${includedir}/tensorflow/lite
    cd ${S}/tensorflow/lite
    for h in $(find . -name "*.h*") $(find . -name "*.cc*"); do
        [ -d ${D}${includedir}/tensorflow/lite/$(dirname $h) ] || install -d ${D}${includedir}/tensorflow/lite/$(dirname $h)
        install -m 0644 $h ${D}${includedir}/tensorflow/lite/$(dirname $h)
    done
    cd -

    # install version.h from core for armnn
    install -d ${D}${includedir}/tensorflow/core/public
    install -m 0644 ${S}/tensorflow/core/public/version.h ${D}${includedir}/tensorflow/core/public

    # install pkgconfig file
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${UNPACKDIR}/tensorflowlite.pc.in ${D}${libdir}/pkgconfig/tensorflowlite.pc
    sed -i 's:@version@:${PV}:g
        s:@libdir@:${libdir}:g
        s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/tensorflowlite.pc
}

FILES:${PN}-dev = ""

INSANE_SKIP:${PN} = "dev-so"

FILES:${PN} += "${libdir}/*.so* ${AIF_INSTALL_DIR}"
FILES:${PN}-dev += "${includedir}/* ${libdir}/pkgconfig/*.pc"
