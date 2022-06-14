# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "TensorFlow Lite CPP Library"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=c7e17cca1ef4230861fb7868e96c387e"
# Compute branch info from ${PV} as Base PV...
BPV = "${@'.'.join(d.getVar('PV').split('.')[0:2])}"
DPV = "${@'.'.join(d.getVar('PV').split('.')[0:3])}"

# Since they tag off of something resembling ${PV}, use it.
# Matches v${PV}
SRCREV_tensorflow = "c2363d6d025981c661f8cbecf4c73ca7fbf38caf"
SRCREV_abseil-cpp = "997aaf3a28308eba1b9156aa35ab7bca9688e9f6"
# v1.12.0
SRCREV_flatbuffers = "6df40a2471737b27271bdd9b900ab5f3aec746c7"
SRCREV_eigen = "7b35638ddb99a0298c5d3450de506a8e8e0203d3"
SRCREV_xnnpack = "476eb84d6a8e6f8249d5584d30759c6fbdbf791d"
SRCREV_neon2sse = "1200fe90bb174a6224a525ee60148671a786a71f"
SRCREV_fft2d = "c6fd2dd6d21397baa6653139d31d84540d5449a2"
SRCREV_farmhash = "816a4ae622e964763ca0862d9dbd19324a1eaf45"
SRCREV_ruy = "e6c1b8dc8a8b00ee74e7268aac8b18d7260ab1ce"
SRCREV_gemmlowp = "fda83bdc38b118cc6b56753bd540caa49e570745"
SRCREV_vulkan-headers = "ec2db85225ab410bc6829251bef6c578aaed5868"
SRCREV_egl-headers = "649981109e263b737e7735933c90626c29a306f2"
SRCREV_opengl-headers = "0cb0880d91581d34f96899c86fc1bf35627b4b81"
SRCREV_FORMAT = "tensorflow"

SRC_URI = " \
    git://github.com/tensorflow/tensorflow.git;branch=r${BPV};protocol=https;name=tensorflow \
    git://github.com/abseil/abseil-cpp.git;branch=lts_2021_03_24;protocol=https;destsuffix=git/abseil-cpp;name=abseil-cpp \
    git://github.com/google/flatbuffers;branch=master;protocol=https;destsuffix=git/flatbuffers;name=flatbuffers \
    git://gitlab.com/libeigen/eigen;branch=master;protocol=https;destsuffix=git/eigen;name=eigen \
    git://github.com/google/XNNPACK;branch=master;protocol=https;destsuffix=git/xnnpack;name=xnnpack \
    git://github.com/intel/ARM_NEON_2_x86_SSE;branch=master;protocol=https;destsuffix=git/neon2sse;name=neon2sse \
    git://github.com/petewarden/OouraFFT;branch=master;protocol=https;destsuffix=git/fft2d;name=fft2d \
    git://github.com/google/farmhash;branch=master;protocol=https;destsuffix=git/farmhash;name=farmhash \
    git://github.com/google/ruy;branch=master;protocol=https;destsuffix=git/ruy;name=ruy \
    git://github.com/google/gemmlowp;branch=master;protocol=https;destsuffix=git/gemmlowp;name=gemmlowp \
    git://github.com/KhronosGroup/Vulkan-Headers;branch=master;protocol=https;destsuffix=git/vulkan_headers;name=vulkan-headers \
    git://github.com/KhronosGroup/EGL-Registry;branch=main;protocol=https;destsuffix=git/egl_headers;name=egl-headers \
    git://github.com/KhronosGroup/OpenGL-Registry;branch=main;protocol=https;destsuffix=git/opengl_headers;name=opengl-headers \
    file://0001-remove-label_image-benchmark_model-exclude-option.patch \
    file://0002-enable-external-delegate-in-benchmarktool.patch \
    file://0003-Fix-return-type-issues.patch \
    file://0004-opencl_wrapper-dlopen-libOpenCL.so.1-instead-of-libO.patch \
    file://0005-auto-delegation-support-when-using-gpu.patch \
    file://tensorflowlite.pc.in \
"

SRC_URI += "https://storage.googleapis.com/download.tensorflow.org/models/inception_v3_2016_08_28_frozen.pb.tar.gz;name=model-inv3"
SRC_URI[model-inv3.md5sum] = "a904ddf15593d03c7dd786d552e22d73"
SRC_URI[model-inv3.sha256sum] = "7045b72a954af4dce36346f478610acdccbf149168fa25c78e54e32f0c723d6d"

SRC_URI += "https://storage.googleapis.com/download.tensorflow.org/models/tflite/mobilenet_v1_1.0_224_quant_and_labels.zip;name=model-mobv1"
SRC_URI[model-mobv1.md5sum] = "38ac0c626947875bd311ef96c8baab62"
SRC_URI[model-mobv1.sha256sum] = "2f8054076cf655e1a73778a49bd8fd0306d32b290b7e576dda9574f00f186c0f"

inherit cmake

PR = "r3"
S = "${WORKDIR}/git"

DEPENDS += " \
    unzip-native \
    python3-native \
    python3-numpy-native \
"

ARM_INSTRUCTION_SET = "arm"

do_generate_toolchain_file:append:arm() {
    # XNNPACK does not recognize this when CMAKE_SYSTEM_PROCESSOR is arm.
    # Instead, you need to change it to armv7.
    sed -i 's:CMAKE_SYSTEM_PROCESSOR arm:CMAKE_SYSTEM_PROCESSOR armv7:g' ${WORKDIR}/toolchain.cmake
}

# tensorflow-lite doesn't respect TUNE_CCARGS correctly and adds its own
# flags like -march=armv8.2-a+fp16+dotprod which then cause:
# rpi4: cc1: warning: switch '-mcpu=cortex-a72' conflicts with '-march=armv8.2-a+fp16+dotprod' switch
# rpi3: cc1: warning: switch '-mcpu=armv8-a' conflicts with '-march=armv8.2-a' switch
# and:
# http://gecko.lge.com/Errors/Details/415456
# tensorflow-lite/2.6.2-r1/build/xnnpack/src/f16-gemm/gen-inc/1x16inc-minmax-aarch64-neonfp16arith-ld32.S: Assembler messages:
# tensorflow-lite/2.6.2-r1/build/xnnpack/src/f16-gemm/gen-inc/1x16inc-minmax-aarch64-neonfp16arith-ld32.S:64: Error: selected processor does not support `fmla v16.8h,v20.8h,v0.h[0]'
# -mcpu=cortex-a72+crc+crypto is used for raspberrypi4-64 with dunfell
# -mcpu=cortex-a72 with kirkstone and newer where crypto is disabled since oe-core commit 2568d537087adb0b592aa250bf628a7b48c3a9d3
MCPU_OPTS_TO_REMOVE = "-mcpu=cortex-a72+crc+crypto -mcpu=cortex-a72 -mcpu=cortex-a53+crc -mcpu=cortex-a53"
TUNE_CCARGS:remove = "${MCPU_OPTS_TO_REMOVE}"

OECMAKE_SOURCEPATH = "${S}/tensorflow/lite"

PACKAGECONFIG ?= "xnnpack"

PACKAGECONFIG[xnnpack] = "-DTFLITE_ENABLE_XNNPACK=ON,-DTFLITE_ENABLE_XNNPACK=OFF"
# opencl_wrapper only dlopens libOpenCL.so.1, so do_package shlibs cannot dynamically add the runtime dependency, add it explicitly here
PACKAGECONFIG[gpu] = "-DTFLITE_ENABLE_GPU=ON,-DTFLITE_ENABLE_GPU=OFF,opencl-headers opencl-icd-loader,opencl-icd-loader"

# There are many external dependencies fetched in do_configure if not found:
# tensorflow/lite/tools/cmake/modules/Findgoogletest.cmake:OverridableFetchContent_GetProperties(googletest)
# tensorflow/lite/tools/cmake/modules/Findnsync.cmake:OverridableFetchContent_GetProperties(nsync)
# tensorflow/lite/tools/cmake/modules/Findre2.cmake:OverridableFetchContent_GetProperties(re2)
# tensorflow/lite/tools/cmake/modules/abseil-cpp.cmake:OverridableFetchContent_GetProperties(abseil-cpp)
# tensorflow/lite/tools/cmake/modules/egl_headers.cmake:OverridableFetchContent_GetProperties(egl_headers)
# tensorflow/lite/tools/cmake/modules/eigen.cmake:OverridableFetchContent_GetProperties(eigen)
# tensorflow/lite/tools/cmake/modules/farmhash.cmake:OverridableFetchContent_GetProperties(farmhash)
# tensorflow/lite/tools/cmake/modules/fft2d.cmake:OverridableFetchContent_GetProperties(fft2d)
# tensorflow/lite/tools/cmake/modules/flatbuffers.cmake:OverridableFetchContent_GetProperties(flatbuffers)
# tensorflow/lite/tools/cmake/modules/fp16_headers.cmake:OverridableFetchContent_GetProperties(fp16_headers)
# tensorflow/lite/tools/cmake/modules/gemmlowp.cmake:OverridableFetchContent_GetProperties(gemmlowp)
# tensorflow/lite/tools/cmake/modules/neon2sse.cmake:OverridableFetchContent_GetProperties(neon2sse)
# tensorflow/lite/tools/cmake/modules/opencl_headers.cmake:OverridableFetchContent_GetProperties(opencl_headers)
# tensorflow/lite/tools/cmake/modules/opengl_headers.cmake:OverridableFetchContent_GetProperties(opengl_headers)
# tensorflow/lite/tools/cmake/modules/ruy.cmake:OverridableFetchContent_GetProperties(ruy)
# tensorflow/lite/tools/cmake/modules/vulkan_headers.cmake:OverridableFetchContent_GetProperties(vulkan_headers)
# tensorflow/lite/tools/cmake/modules/xnnpack.cmake:OverridableFetchContent_GetProperties(xnnpack)
do_configure:prepend() {
    for i in abseil-cpp flatbuffers eigen xnnpack neon2sse fft2d farmhash ruy gemmlowp vulkan_headers egl_headers opengl_headers; do
        cp -ra ${S}/$i ${B}/$i
    done
}

EXTRA_OECMAKE = "-DBUILD_SHARED_LIBS=ON"

AIF_INSTALL_DIR = "${datadir}/aif"

do_install() {
    # install libraries
    install -d ${D}/${libdir}
    install -m 0644 $(find . -name "*.so") ${D}${libdir}

    # armnn expects libtensorflowlite.so not libtensorflow-lite.sh:
    # $ grep libtensorflowlite /OE/lge/build/webos/dunfell/BUILD/work/qemux86_64-webos-linux/armnn/21.11-r1/git/delegate/cmake/Modules/FindTfLite.cmake
    # find_library(TfLite_LIB NAMES "libtensorflow_lite_all.so" "libtensorflowlite.so" HINTS ${TFLITE_LIB_ROOT} ${TFLITE_LIB_ROOT}/tensorflow/lite)
    ln -snf libtensorflow-lite.so ${D}/${libdir}/libtensorflowlite.so

    # install benchmark_model
    install -d ${D}${AIF_INSTALL_DIR}
    install -m 755 ${B}/tools/benchmark/benchmark_model \
        ${D}${AIF_INSTALL_DIR}

    # install header files
    install -d ${D}${includedir}/tensorflow/lite
    cd ${S}/tensorflow/lite
    for h in $(find . -name "*.h*"); do
        [ -d ${D}${includedir}/tensorflow/lite/$(dirname $h) ] || install -d ${D}${includedir}/tensorflow/lite/$(dirname $h)
        install -m 0644 $h ${D}${includedir}/tensorflow/lite/$(dirname $h)
    done
    cd -

    # install version.h from core for armnn
    install -d ${D}${includedir}/tensorflow/core/public
    install -m 0644 ${S}/tensorflow/core/public/version.h ${D}${includedir}/tensorflow/core/public

    # install xnnpack.h from xnnpack for libtorch
    install -m 0644 ${B}/xnnpack/include/xnnpack.h ${D}${includedir}

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
