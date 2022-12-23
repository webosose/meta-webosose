# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "TensorFlow Lite CPP Library"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

BRANCH = "r${@oe.utils.trim_version('${PV}', 2)}"

SRCREV_FORMAT = "tensorflow"
# Matches v${PV} tag
SRCREV_tensorflow = "a5ed5f39b675a1c6f315e0caf3ad4b38478fa571"

# v1.12.0
SRCREV_flatbuffers = "6df40a2471737b27271bdd9b900ab5f3aec746c7"
SRCREV_eigen = "008ff3483a8c5604639e1c4d204eae30ad737af6"
SRCREV_neon2sse = "1200fe90bb174a6224a525ee60148671a786a71f"
SRCREV_fft2d = "c6fd2dd6d21397baa6653139d31d84540d5449a2"
SRCREV_farmhash = "0d859a811870d10f53a594927d0d0b97573ad06d"
SRCREV_ruy = "e6c1b8dc8a8b00ee74e7268aac8b18d7260ab1ce"
SRCREV_gemmlowp = "fda83bdc38b118cc6b56753bd540caa49e570745"
SRCREV_vulkan-headers = "ec2db85225ab410bc6829251bef6c578aaed5868"
SRCREV_egl-headers = "649981109e263b737e7735933c90626c29a306f2"
SRCREV_opengl-headers = "0cb0880d91581d34f96899c86fc1bf35627b4b81"

SRC_URI += " \
    git://github.com/tensorflow/tensorflow.git;branch=${BRANCH};protocol=https;name=tensorflow \
    git://github.com/google/flatbuffers;branch=master;protocol=https;destsuffix=git/flatbuffers;name=flatbuffers \
    git://gitlab.com/libeigen/eigen;branch=master;protocol=https;destsuffix=git/eigen;name=eigen \
    git://github.com/intel/ARM_NEON_2_x86_SSE;branch=master;protocol=https;destsuffix=git/neon2sse;name=neon2sse \
    git://github.com/petewarden/OouraFFT;branch=master;protocol=https;destsuffix=git/fft2d;name=fft2d \
    git://github.com/google/farmhash;branch=master;protocol=https;destsuffix=git/farmhash;name=farmhash \
    git://github.com/google/ruy;branch=master;protocol=https;destsuffix=git/ruy;name=ruy \
    git://github.com/google/gemmlowp;branch=master;protocol=https;destsuffix=git/gemmlowp;name=gemmlowp \
    git://github.com/KhronosGroup/Vulkan-Headers;branch=main;protocol=https;destsuffix=git/vulkan_headers;name=vulkan-headers \
    git://github.com/KhronosGroup/EGL-Registry;branch=main;protocol=https;destsuffix=git/egl_headers;name=egl-headers \
    git://github.com/KhronosGroup/OpenGL-Registry;branch=main;protocol=https;destsuffix=git/opengl_headers;name=opengl-headers \
    file://0001-Fix-clog-cpuinfo-configure-error.patch \
    file://0002-opencl_wrapper-dlopen-libOpenCL.so.1-instead-of-libO.patch \
    file://0003-auto-delegation-support-when-using-gpu.patch \
    file://0004-add-gpu-delegate-option-for-pytorch-converted-models.patch \
    file://0005-enable-build-with-only-opengl-in-rp4.patch \
    file://0006-Fix-return-type-issues.patch \
"

inherit cmake

PR = "r0"
S = "${WORKDIR}/git"

DEPENDS += " \
    unzip-native \
    python3-native \
    python3-numpy-native \
    abseil-cpp \
    xnnpack \
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
# tensorflow-lite/2.9.2-r0/build/xnnpack/src/f16-gemm/gen-inc/1x16inc-minmax-aarch64-neonfp16arith-ld32.S: Assembler messages:
# tensorflow-lite/2.9.2-r0/build/xnnpack/src/f16-gemm/gen-inc/1x16inc-minmax-aarch64-neonfp16arith-ld32.S:64: Error: selected processor does not support `fmla v16.8h,v20.8h,v0.h[0]'
# -mcpu=cortex-a72+crc+crypto is used for raspberrypi4-64 with dunfell
# -mcpu=cortex-a72 with kirkstone and newer where crypto is disabled since oe-core commit 2568d537087adb0b592aa250bf628a7b48c3a9d3
MCPU_OPTS_TO_REMOVE = "-mcpu=cortex-a72+crc+crypto -mcpu=cortex-a72 -mcpu=cortex-a53+crc -mcpu=cortex-a53"
TUNE_CCARGS:remove = "${MCPU_OPTS_TO_REMOVE}"

OECMAKE_SOURCEPATH = "${S}/tensorflow/lite"

PACKAGECONFIG ?= "xnnpack"
PACKAGECONFIG += "${@bb.utils.contains('DISTRO_FEATURES', 'gpu-delegate', 'gpu gl-backend', '', d)}"

PACKAGECONFIG[xnnpack] = "-DTFLITE_ENABLE_XNNPACK=ON,-DTFLITE_ENABLE_XNNPACK=OFF"
PACKAGECONFIG[gpu] = "-DTFLITE_ENABLE_GPU=ON,-DTFLITE_ENABLE_GPU=OFF"
PACKAGECONFIG[gl-backend] = "-DTFLITE_ENABLE_GPU_GL_ONLY=ON, -DTFLITE_ENABLE_GPU_GL_ONLY=OFF, virtual/egl virtual/libgles2"

# There are many external dependencies fetched in do_configure if not found:
# tensorflow/lite/tools/cmake/modules/Findgoogletest.cmake:OverridableFetchContent_GetProperties(googletest)
# tensorflow/lite/tools/cmake/modules/Findnsync.cmake:OverridableFetchContent_GetProperties(nsync)
# tensorflow/lite/tools/cmake/modules/Findre2.cmake:OverridableFetchContent_GetProperties(re2)
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
    for i in flatbuffers eigen neon2sse fft2d farmhash ruy gemmlowp vulkan_headers egl_headers opengl_headers; do
        cp -ra ${S}/$i ${B}/$i
    done
    # this prevents abslConfig.cmake to be found from system absl
    rm -f ${OECMAKE_SOURCEPATH}/tools/cmake/modules/Findabsl.cmake
    rm -f ${OECMAKE_SOURCEPATH}/tools/cmake/modules/Findxnnpack.cmake
}

# For these we can set *_SOURCE_DIR to avoid copy in do_configure:prepend:
# tensorflow-lite/2.9.3-r3/git $ grep SOURCE_DIR xnnpack/CMakeLists.txt | grep Downloading
#    MESSAGE(STATUS "Downloading clog to ${CMAKE_BINARY_DIR}/clog-source (define CLOG_SOURCE_DIR to avoid it)")
#    MESSAGE(STATUS "Downloading cpuinfo to ${CMAKE_BINARY_DIR}/cpuinfo-source (define CPUINFO_SOURCE_DIR to avoid it)")
#    MESSAGE(STATUS "Downloading FP16 to ${CMAKE_BINARY_DIR}/FP16-source (define FP16_SOURCE_DIR to avoid it)")
#    MESSAGE(STATUS "Downloading FXdiv to ${CMAKE_BINARY_DIR}/FXdiv-source (define FXDIV_SOURCE_DIR to avoid it)")
#    MESSAGE(STATUS "Downloading pthreadpool to ${CMAKE_BINARY_DIR}/pthreadpool-source (define PTHREADPOOL_SOURCE_DIR to avoid it)")
#    MESSAGE(STATUS "Downloading Google Test to ${CMAKE_BINARY_DIR}/googletest-source (define GOOGLETEST_SOURCE_DIR to avoid it)")
#    MESSAGE(STATUS "Downloading Google Benchmark to ${CMAKE_BINARY_DIR}/googlebenchmark-source (define GOOGLEBENCHMARK_SOURCE_DIR to avoid it)")
# tensorflow-lite/2.9.3-r3/git $ grep SOURCE_DIR FP16-source/CMakeLists.txt | grep Downloading
#    MESSAGE(STATUS "Downloading PSimd to ${CMAKE_BINARY_DIR}/psimd-source (define PSIMD_SOURCE_DIR to avoid it)")
#    MESSAGE(STATUS "Downloading Google Test to ${CMAKE_BINARY_DIR}/googletest-source (define GOOGLETEST_SOURCE_DIR to avoid it)")
#    MESSAGE(STATUS "Downloading Google Benchmark to ${CMAKE_BINARY_DIR}/googlebenchmark-source (define GOOGLEBENCHMARK_SOURCE_DIR to avoid it)")

EXTRA_OECMAKE += " \
    -DCLOG_SOURCE_DIR=${S}/clog-source \
    -DCPUINFO_SOURCE_DIR=${S}/cpuinfo-source \
    -DFP16_SOURCE_DIR=${S}/FP16-source \
    -DFXDIV_SOURCE_DIR=${S}/FXdiv-source \
    -DPTHREADPOOL_SOURCE_DIR=${S}/pthreadpool-source \
    -DPSIMD_SOURCE_DIR=${S}/psimd-source \
"
