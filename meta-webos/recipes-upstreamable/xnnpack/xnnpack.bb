# Copyright (c) 2022-2025 LG Electronics, Inc.
SUMMARY = "XNNPACK is a based on QNNPACK library"

DESCRIPTION = "XNNPACK is a highly optimized library of floating-point neural network inference \
   operators for ARM, WebAssembly, and x86 platforms. XNNPACK is not intended \
   for direct use by deep learning practitioners and researchers; \
   instead it provides low-level performance primitives for accelerating high-level machine learning frameworks, such as \
   TensorFlow Lite, TensorFlow.js, PyTorch, and MediaPipe."

HOMEPAGE = "https://github.com/google/XNNPACK"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=afa8f8a91390ab659c837da57124977c"

# Matches v${PV} tag
PV = "475884903"
SRCREV_main = "e8f74a9763aa36559980a0c2f37f587794995622"

#xnnpack/475884903-r0/git/cmake$ grep -rn "URL https"
#DownloadCpuinfo.cmake:15:  URL https://github.com/pytorch/cpuinfo/archive/49610f89b8b1eb52d75d1eda7a2c40c1e86a78e7.zip
#DownloadCLog.cmake:15:  URL https://github.com/pytorch/cpuinfo/archive/4b5a76c4de21265ddba98fc8f259e136ad11411b.zip
#DownloadFP16.cmake:15:  URL https://github.com/Maratyszcza/FP16/archive/0a92994d729ff76a58f692d3028ca1b64b145d91.zip
#DownloadFXdiv.cmake:15:  URL https://github.com/Maratyszcza/FXdiv/archive/b408327ac2a15ec3e43352421954f5b1967701d1.zip
#DownloadGoogleBenchmark.cmake:15:  URL https://github.com/google/benchmark/archive/refs/tags/v1.5.5.zip
#DownloadGoogleTest.cmake:15:  URL https://github.com/google/googletest/archive/5a509dbd2e5a6c694116e329c5a20dc190653724.zip
#DownloadPThreadPool.cmake:15:  URL https://github.com/Maratyszcza/pthreadpool/archive/545ebe9f225aec6dca49109516fac02e973a3de2.zip

SRCREV_clog = "4b5a76c4de21265ddba98fc8f259e136ad11411b"
SRCREV_cpuinfo = "49610f89b8b1eb52d75d1eda7a2c40c1e86a78e7"
SRCREV_fp16 = "0a92994d729ff76a58f692d3028ca1b64b145d91"
SRCREV_fxdiv = "b408327ac2a15ec3e43352421954f5b1967701d1"
SRCREV_pthreadpool = "545ebe9f225aec6dca49109516fac02e973a3de2"

# Following is for FP16:
# FP16-source/cmake/DownloadPSimd.cmake:  GIT_REPOSITORY https://github.com/Maratyszcza/psimd.git
# FP16-source/cmake/DownloadPSimd.cmake:  GIT_TAG master
SRCREV_psimd = "072586a71b55b7f8c584153d223e95687148a900"

SRCREV_FORMAT = "main_cpuinfo_clog_fp16_fxdif_pthreadpool_psimd"

SRC_URI += " \
    git://github.com/google/XNNPACK;branch=master;name=main;protocol=https \
    git://github.com/pytorch/cpuinfo;branch=main;protocol=https;destsuffix=git/cpuinfo-source;name=cpuinfo \
    git://github.com/pytorch/cpuinfo;branch=main;protocol=https;destsuffix=git/clog-source;name=clog \
    git://github.com/Maratyszcza/FP16;branch=master;protocol=https;destsuffix=git/FP16-source;name=fp16 \
    git://github.com/Maratyszcza/FXdiv;branch=master;protocol=https;destsuffix=git/FXdiv-source;name=fxdiv \
    git://github.com/Maratyszcza/pthreadpool;branch=master;protocol=https;destsuffix=git/pthreadpool-source;name=pthreadpool \
    git://github.com/Maratyszcza/psimd;branch=master;protocol=https;destsuffix=git/psimd-source;name=psimd \
    file://0001-Fix-return-type-issues.patch \
    file://git/CMakeLists.txt \
    file://git/cmake/xnnpack-config.cmake.in \
    file://git/cpuinfo-source/CMakeLists.txt \
    file://git/cpuinfo-source/cmake/cpuinfo-config.cmake.in \
    file://git/pthreadpool-source/CMakeLists.txt \
    file://git/pthreadpool-source/cmake/pthreadpool-config.cmake.in \
"

inherit cmake

PR = "r3"
S = "${WORKDIR}/git"

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
    -DXNNPACK_BUILD_TESTS=OFF \
    -DXNNPACK_BUILD_BENCHMARKS=OFF \
    -DCLOG_SOURCE_DIR=${S}/clog-source \
    -DCPUINFO_SOURCE_DIR=${S}/cpuinfo-source \
    -DFP16_SOURCE_DIR=${S}/FP16-source \
    -DFXDIV_SOURCE_DIR=${S}/FXdiv-source \
    -DPTHREADPOOL_SOURCE_DIR=${S}/pthreadpool-source \
    -DPSIMD_SOURCE_DIR=${S}/psimd-source \
"

EXTRA_OECMAKE += "-DBUILD_SHARED_LIBS=ON"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""

FILES:${PN} += "${datadir}/*"

INSANE_SKIP:${PN} = "textrel"

# many cases of:
# git/src/f16-dwconv2d-chw/gen/5x5s2p2-minmax-neonfp16arith-3x4.c:37:41: error: passing argument 1 of 'vld1_dup_f16' from incompatible pointer type [-Wincompatible-pointer-types]
# http://gecko.lge.com:8000/Errors/Details/821678
CFLAGS += "-Wno-error=incompatible-pointer-types"
