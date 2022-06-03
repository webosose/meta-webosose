# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "TensorFlow Lite CPP Library"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=c7e17cca1ef4230861fb7868e96c387e"
# Compute branch info from ${PV} as Base PV...
BPV = "${@'.'.join(d.getVar('PV').split('.')[0:2])}"
DPV = "${@'.'.join(d.getVar('PV').split('.')[0:3])}"

# Since they tag off of something resembling ${PV}, use it.
# Matches v${PV}
SRCREV = "c2363d6d025981c661f8cbecf4c73ca7fbf38caf"

SRC_URI = " \
    git://github.com/tensorflow/tensorflow.git;branch=r${BPV};protocol=https \
    file://0001-remove-label_image-benchmark_model-exclude-option.patch \
    file://0002-enable-external-delegate-in-benchmarktool.patch \
    file://tensorflowlite.pc.in \
"

SRC_URI += "https://storage.googleapis.com/download.tensorflow.org/models/inception_v3_2016_08_28_frozen.pb.tar.gz;name=model-inv3"
SRC_URI[model-inv3.md5sum] = "a904ddf15593d03c7dd786d552e22d73"
SRC_URI[model-inv3.sha256sum] = "7045b72a954af4dce36346f478610acdccbf149168fa25c78e54e32f0c723d6d"

SRC_URI += "https://storage.googleapis.com/download.tensorflow.org/models/tflite/mobilenet_v1_1.0_224_quant_and_labels.zip;name=model-mobv1"
SRC_URI[model-mobv1.md5sum] = "38ac0c626947875bd311ef96c8baab62"
SRC_URI[model-mobv1.sha256sum] = "2f8054076cf655e1a73778a49bd8fd0306d32b290b7e576dda9574f00f186c0f"

inherit cmake

PR = "r0"
S = "${WORKDIR}/git"

DEPENDS += " \
    unzip-native \
    python3-native \
    python3-numpy-native \
"

ARM_INSTRUCTION_SET = "arm"

TFLITE_TUNE_CCARGS := "${@bb.utils.contains('TARGET_ARCH', 'aarch64', '-funsafe-math-optimizations', '${TUNE_CCARGS} -funsafe-math-optimizations', d)}"
TUNE_CCARGS = "${TFLITE_TUNE_CCARGS}"
TUNE_CCARGS += "${@bb.utils.contains('MACHINE', 'raspberrypi4', '-marm -mfpu=neon-vfpv4 -mfloat-abi=hard -march=armv7-a', '', d)}"
TUNE_CCARGS += "${@bb.utils.contains('MACHINE', 'o22', '-marm -mfpu=neon -mfloat-abi=softfp -mcpu=cortex-a9 -mtune=cortex-a9 -funwind-tables -rdynamic', '', d)}"
TUNE_CCARGS += "${@bb.utils.contains('MACHINE', 'o20', '-marm -mfpu=neon -mfloat-abi=softfp -mcpu=cortex-a9 -mtune=cortex-a9 -funwind-tables', '', d)}"

do_generate_toolchain_file:append() {

    # XNNPACK does not recognize this when CMAKE_SYSTEM_PROCESSOR is arm.
    # Instead, you need to change it to armv7.
    if ${@bb.utils.contains('TARGET_ARCH', 'arm', 'true', 'false', d)}; then
        sed -i 's:CMAKE_SYSTEM_PROCESSOR arm:CMAKE_SYSTEM_PROCESSOR armv7:g' ${WORKDIR}/toolchain.cmake
    fi

    # NOTE: Werror=reture-type is recognized as an error when there is no return value from a non void function.
    # However, if this option is included, a build error occurred in tensorflow lite and was omitted.
    sed -i 's:-Werror=return-type::g' ${WORKDIR}/toolchain.cmake
}

OECMAKE_SOURCEPATH = "${S}/tensorflow/lite"

PACKAGECONFIG ?= "xnnpack"
PACKAGECONFIG += "${@bb.utils.contains('MACHINE', 'o22', 'gpu', '', d)}"

PACKAGECONFIG[xnnpack] = " \
    -DTFLITE_ENABLE_XNNPACK=ON, \
    -DTFLITE_ENABLE_XNNPACK=OFF \
"

PACKAGECONFIG[gpu] = " \
    -DTFLITE_ENABLE_GPU=ON, \
    -DTFLITE_ENABLE_GPU=OFF, \
    opencl-headers opencl-icd-loader \
"

EXTRA_OECMAKE = " -DBUILD_SHARED_LIBS=ON"

AIF_INSTALL_DIR = "${datadir}/aif"

do_install() {
    # install libraries
    install -d ${D}/${libdir}

    cd ${B}
    cp \
        $(find . -name "*.so") \
        ${D}${libdir}

    ln -s -r ${D}/${libdir}/libtensorflow-lite.so ${D}/${libdir}/libtensorflowlite.so

    # install benchmark_model
    install -d ${D}${AIF_INSTALL_DIR}
    install -m 755 ${B}/tools/benchmark/benchmark_model \
        ${D}${AIF_INSTALL_DIR}

    # install header files
    install -d ${D}${includedir}/tensorflow/lite
    cd ${S}/tensorflow/lite
    cp --parents \
        $(find . -name "*.h*") \
        ${D}${includedir}/tensorflow/lite

    # install version.h from core for armnn
    install -d ${D}${includedir}/tensorflow/core/public
    cp ${S}/tensorflow/core/public/version.h ${D}${includedir}/tensorflow/core/public

    # install xnnpack.h from xnnpack for libtorch
    cp ${B}/xnnpack/include/xnnpack.h ${D}${includedir}

    # install pkgconfig file
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/tensorflowlite.pc.in ${D}${libdir}/pkgconfig/tensorflowlite.pc
    sed -i 's:@version@:${PV}:g
        s:@libdir@:${libdir}:g
        s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/tensorflowlite.pc
}

FILES:${PN}-dev = ""

INSANE_SKIP:${PN} = "dev-so"

FILES:${PN} += "${libdir}/*.so* ${AIF_INSTALL_DIR}"
FILES:${PN}-dev += "${includedir}/* ${libdir}/pkgconfig/*.pc"
