SUMMARY = "The ARM Computer Vision and Machine Learning library"
DESCRIPTION = "The ARM Computer Vision and Machine Learning library is a set of functions optimised for both ARM CPUs and GPUs."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f3c5879801d3cffc4ac2399f2b8e8ec5"

SRCBRANCH = "main"
# Matches v${PV}
SRCREV = "aabef6c0584f06f4c0f4b61fb787d80374240619"

SRC_URI = " \
    git://github.com/ARM-software/ComputeLibrary.git;branch=${SRCBRANCH};protocol=https \
    file://0001-webos-build-support.patch \
    file://0001-OpenCL-dlopen-libOpenCL.so.1-instead-of-libO.patch \
    file://0001-Fix-build-with-gcc-13.patch \
    file://arm-compute-library.pc.in \
"

PR = "r1"

S = "${WORKDIR}/git"

inherit scons

ARM_INSTRUCTION_SET = "arm"

PACKAGECONFIG ?= "cppthreads opencl embed openmp examples tests"

PACKAGECONFIG[Werror] = "Werror=1,Werror=0"
PACKAGECONFIG[tests] = "benchmark_tests=1 validation_tests=1,benchmark_tests=0 validation_tests=0"
PACKAGECONFIG[opencl] = "opencl=1,opencl=0,opencl-headers opencl-icd-loader"
PACKAGECONFIG[embed] = "embed_kernels=1,embed_kernels=0"
PACKAGECONFIG[compress] = "compress_kernels=1,compress_kernels=0,zlib"
PACKAGECONFIG[debug] = "debug=1,debug=0"
PACKAGECONFIG[openmp] = "openmp=1,openmp=0"
PACKAGECONFIG[cppthreads] = "cppthreads=1,cppthreads=0"
PACKAGECONFIG[examples] = "examples=1,examples=0"

# Specify any options you want to pass to scons using EXTRA_OESCONS:
EXTRA_OESCONS = "build=cross_compile os=linux toolchain_prefix=' ' extra_cxx_flags='-fPIC -O2' ${PACKAGECONFIG_CONFARGS}"

EXTRA_OESCONS:append:aarch64 = " arch=arm64-v8a neon=1"
EXTRA_OESCONS:append:arm = " os=webos neon=1 arch=armv7a${@ '-hf' if (d.getVar('TUNE_CCARGS_MFLOAT') == 'hard') else ''}"
EXTRA_OESCONS:append:x86 = " arch=x86_32 neon=0 estate=32"
EXTRA_OESCONS:append:x86-64 = " arch=x86_64 neon=0"

TARGET_CC_ARCH += "${LDFLAGS}"

#CXXFLAGS += "-fopenmp"
LIBS += "-larmpl_lp64_mp"

do_install() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"
    install -d ${D}${includedir}
    cp $CP_ARGS ${S}/arm_compute ${D}${includedir}
    cp $CP_ARGS support ${D}${includedir}
    cp $CP_ARGS include/half ${D}${includedir}

    # install libraries
    install -d ${D}${libdir}
    install -m 0755 ${S}/build/libarm_compute*.so ${D}${libdir}
    install -m 0755 ${S}/build/libarm_compute*.a ${D}${libdir}

    # install examples
    if ${@bb.utils.contains('PACKAGECONFIG', 'examples', 'true', 'false', d)}; then
        install -d ${D}${bindir}/${PN}-${PV}/examples
        for example in ${S}/build/examples/*; do
            if [ -d "$example" ]; then
                continue
            fi
            case "$example" in
                (*.o|*.a) continue;;
            esac
            install -m 0555 $example ${D}${bindir}/${PN}-${PV}/examples
        done
        if [ -d "${S}/build/examples/gemm_tuner" ]; then
            install -d ${D}${bindir}/${PN}-${PV}/examples/gemm_tuner
            for example in ${S}/build/examples/gemm_tuner/*; do
                if [ -d "$example" ]; then
                    continue
                fi
                case "$example" in
                    (*.o|*.a) continue;;
                esac
                install -m 0555 $example ${D}${bindir}/${PN}-${PV}/examples/gemm_tuner
            done
        fi
    fi

    # install tests
    if ${@bb.utils.contains('PACKAGECONFIG', 'tests', 'true', 'false', d)}; then
        install -d ${D}${bindir}/${PN}-${PV}/tests
        for test in ${S}/build/tests/*; do
            if [ -d "$test" ]; then
                continue
            fi
            case "$test" in
                (*.o|*.a) continue;;
            esac
            install -m 0555 $test ${D}${bindir}/${PN}-${PV}/tests
        done
        if [ -d "${S}/build/tests/gemm_tuner" ]; then
            install -d ${D}${bindir}/${PN}-${PV}/tests/gemm_tuner
            for test in ${S}/build/tests/gemm_tuner/*; do
                if [ -d "$test" ]; then
                    continue
                fi
                case "$test" in
                    (*.o|*.a) continue;;
                esac
                install -m 0555 $test ${D}${bindir}/${PN}-${PV}/tests/gemm_tuner
            done
        fi
    fi

    #install pkgconfig
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/arm-compute-library.pc.in ${D}${libdir}/pkgconfig/arm-compute-library.pc
    sed -i 's:@version@:${PV}:g
        s:@libdir@:${libdir}:g
        s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/arm-compute-library.pc
}

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# package unversioned .so files in PN (they are not dev symlinks)
FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/*.so"

PACKAGES =+ "${PN}-tests ${PN}-examples"
FILES:${PN}-tests = "${bindir}/*/tests"
FILES:${PN}-examples = "${bindir}/*/examples"
RDEPENDS:${PN}-tests += "${PN}"
RDEPENDS:${PN}-examples += "${PN}"
