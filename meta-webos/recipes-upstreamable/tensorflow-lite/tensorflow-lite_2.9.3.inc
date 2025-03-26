# Copyright (c) 2022-2025 LG Electronics, Inc.

SUMMARY = "TensorFlow Lite CPP Library"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

BRANCH = "r${@oe.utils.trim_version('${PV}', 2)}"

SRCREV_FORMAT = "tensorflow"
# Matches v${PV} tag
SRCREV_tensorflow = "a5ed5f39b675a1c6f315e0caf3ad4b38478fa571"

# v2.0.6
SRCREV_flatbuffers = "3d79a88adb0eceb2ab5ff994c9b4c03b4b3c0daf"
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
    file://0007-enable-nnapi-delegate.patch \
    file://0008-Fix-memory-leak-from-xnnpack-delegate.patch \
    file://0009-Pass-the-RNN-nodes-not-checking-if-it-iss-half_pixel.patch \
    \
    file://0007-Allow-to-set-XNNpack-flag-options-via-tflite-acceler.patch \
    file://0008-Add-support-for-disabling-default-XNNPack-delegate-i.patch \
    file://0009-Add-RELU_0_TO_1-into-TensorFlow-Lite-schema.patch \
    file://0010-lite-Add-unsorted_segment_prod-support.patch \
    file://0011-lite-Switch-to-Flatbuffer-2.0.5.patch \
    file://0012-Update-FlatBuffer-to-2.0.6.patch \
    file://0013-tensorflow-lite-delegates-gpu-gl-regen-with-flatc-2..patch \
    file://0014-Fix-build-with-gcc-13.patch \
    file://0015-lite-CMakeLists.txt-use-C-17.patch \
    file://CVE2023/0001-CVE-2023-25659-Add-out-of-bounds-array-check-to-dynamic_stitch_op.patch \
    file://CVE2023/0002-CVE-2023-25660-Quick-fix-for-a-vuln-in-printing-empty-tensors.patch \
    file://CVE2023/0003-CVE-2023-25662-Fix-security-vulnerability-in-EditDistance-op-shape-.patch \
    file://CVE2023/0004-CVE-2023-25663-Fixing-null-pointer-read-in-TensorArrayConcat-when-t.patch \
    file://CVE2023/0005-CVE-2023-25664-Add-inputs-check-for-AvgPoolGrad.patch \
    file://CVE2023/0006-CVE-2023-25665-Add-validation-checks-in-sparse-binary-ops.patch \
    file://CVE2023/0007-CVE-2023-25666-Fix-audio-spectrogram-FPE.patch \
    file://CVE2023/0008-CVE-2023-25667-Fix-integer-overflow-for-multiframe-gifs.patch \
    file://CVE2023/0009-CVE-2023-25669-tf2xla-Validate-that-stride-and-window-size-are-posi.patch \
    file://CVE2023/0010-CVE-2023-25670-Check-min-max-values-to-be-scalars.patch \
    file://CVE2023/0011-CVE-2023-25671-1-tfg-Fix-out-of-bounds-access-due-to-mismatched-integ.patch \
    file://CVE2023/0012-CVE-2023-25671-2-tfg-translate-needs-to-call-InitMlir.patch \
    file://CVE2023/0013-CVE-2023-25672-Fixes-shape-inference-of-LookupTableImportV2-to-hand.patch \
    file://CVE2023/0014-CVE-2023-25673-Tensorflow-Fix-security-vulnerability-with-TensorLis.patch \
    file://CVE2023/0015-CVE-2023-25675-Tensorflow-Fix-security-vulnerability-with-DenseBinc.patch \
    file://CVE2023/0016-CVE-2023-25676-Check-for-unexpected-scalars-in-the-shape-argument-t.patch \
    file://CVE2023/0017-CVE-2023-25801-Fix-security-vulnerability-with-FractionalMax-AVG-Po.patch \
    file://CVE2023/0018-CVE-2023-27579-Check-filter_input_channel-0-in-conv-kernel.patch \
    file://0001-stl_emulation-span-count_-is-not-const-anymore-7226-.patch;patchdir=flatbuffers \
"

inherit cmake

PR = "r11"
S = "${WORKDIR}/git"

DEPENDS += " \
    unzip-native \
    python3-native \
    python3-numpy-native \
    abseil-cpp \
    xnnpack \
"

RDEPENDS:${PN} = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'webos-nnapi', '${MLPREFIX}webos-nnruntime', '', d)} \
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
PACKAGECONFIG += "${@bb.utils.contains('COMBINED_FEATURES', 'webos-gpu-delegate', 'gpu', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('MACHINE_FEATURES', 'webos-gl-backend', bb.utils.contains_any('DISTRO_FEATURES', 'vulkan opengl', 'gl-backend', '', d), '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('MACHINE_FEATURES', 'webos-nnapi', 'nnapi', '', d)}"

PACKAGECONFIG[xnnpack] = "-DTFLITE_ENABLE_XNNPACK=ON,-DTFLITE_ENABLE_XNNPACK=OFF"
PACKAGECONFIG[gpu] = "-DTFLITE_ENABLE_GPU=ON,-DTFLITE_ENABLE_GPU=OFF"
PACKAGECONFIG[gl-backend] = "-DTFLITE_ENABLE_GPU_GL_ONLY=ON, -DTFLITE_ENABLE_GPU_GL_ONLY=OFF, virtual/egl virtual/libgles2"
PACKAGECONFIG[nnapi] = "-DTFLITE_ENABLE_NNAPI=ON,-DTFLITE_ENABLE_NNAPI=OFF"

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

# with -mcpu=cortex-a76+crypto used instead of -mcpu=cortex-a76 + -march=armv8.2-a+crypto
# after http://gpro.lge.com/c/bsp/samsung/meta-samsung-bsp/+/404399 tune-cortexa76.inc: delete, the same is in oe-core
# https://git.openembedded.org/openembedded-core/commit/meta/conf/machine/include/arm/armv8-2a?h=scarthgap&id=e64f0c1b6ac5d598a79a21de5f3060f83cb9523e
# __ARM_FEATURE_DOTPROD is defined (see https://github.com/gcc-mirror/gcc/commit/ba09dd21b342bb8b7ef8122c08ee478e7de95825)
# and build fails with:
# http://gecko.lge.com:8000/Errors/Details/826324
# git/tensorflow/lite/kernels/internal/optimized/depthwiseconv_3x3_filter_common.h:132:58: error: cannot convert '__Int32x2_t' to 'int8x8_t'
# git/tensorflow/lite/kernels/internal/optimized/depthwiseconv_3x3_filter_common.h:134:58: error: cannot convert '__Int32x2_t' to 'int8x8_t'
# git/tensorflow/lite/kernels/internal/optimized/depthwiseconv_3x3_filter_common.h:136:58: error: cannot convert '__Int32x2_t' to 'int8x8_t'
# git/tensorflow/lite/kernels/internal/optimized/depthwiseconv_3x3_filter_common.h:140:58: error: cannot convert '__Int32x2_t' to 'int8x8_t'
# use -flax-vector-conversions as suggested in:
# https://stackoverflow.com/questions/56055359/tensorflow-lite-arm64-error-cannot-convert-const-int8x8-t
# until upgrading to newer version which will have this resolved properly
CXXFLAGS += "-flax-vector-conversions"
