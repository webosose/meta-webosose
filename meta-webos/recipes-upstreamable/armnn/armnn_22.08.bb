SUMMARY = "ARM Neural Network SDK"
DESCRIPTION = "Linux software and tools to enable machine learning workloads on power-efficient devices"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3e14a924c16f7d828b8335a59da64074"

# Compute branch info from ${PV} as Base PV...
BPV = "${@'_'.join(d.getVar('PV').split('.')[0:2])}"

BRANCH = "branches/armnn_${BPV}"

SRC_URI = " \
    git://github.com/ARM-software/armnn.git;branch=${BRANCH};protocol=https \
    file://0001-Fix-build-with-gcc-13.patch \
    file://armnn-tflite.pc.in \
    file://armnn-delegate.pc.in \
"

# Matches v${PV}
SRCREV = "0ba0b2bf80f1d7aff1eff8de2b67eb04081b2af0"

PR = "r2"

S = "${WORKDIR}/git"

inherit cmake
inherit pkgconfig

DEPENDS = " \
    boost \
    protobuf \
    stb \
    half \
    xxd-native \
    arm-compute-library \
"

RDEPENDS_WEBOS = ""

RDEPENDS:${PN}   = " \
    arm-compute-library \
    protobuf \
    boost \
    ${RDEPENDS_WEBOS} \
"

PACKAGECONFIG += "ref opencl tensorflow-lite tensorflow-lite-delegate"
PACKAGECONFIG += "${@bb.utils.contains('TARGET_ARCH', 'aarch64', 'neon', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('TARGET_ARCH', 'arm', 'neon', '', d)}"

PACKAGECONFIG[neon] = " \
    -DARMCOMPUTENEON=1, \
    -DARMCOMPUTENEON=0 \
"

PACKAGECONFIG[opencl] = " \
    -DARMCOMPUTECL=1 \
    -DFLATC=${STAGING_BINDIR_NATIVE}/flatc \
    -DOPENCL_INCLUDE=${STAGING_INCDIR}, \
    -DARMCOMPUTECL=0, \
    opencl-headers opencl-clhpp flatbuffers flatbuffers-native \
"

PACKAGECONFIG[tensorflow-lite] = " \
    -DTF_LITE_GENERATED_PATH=${STAGING_DIR_TARGET}/usr/include/tensorflow/lite/schema \
    -DTfLite_INCLUDE_DIR=${STAGING_DIR_TARGET}/usr/include/tensorflow-lite \
    -DTfLite_Schema_INCLUDE_PATH=${STAGING_DIR_TARGET}/usr/include/tensorflow/lite/schema \
    -DBUILD_TF_LITE_PARSER=1, \
    -DBUILD_TF_LITE_PARSER=0, \
    flatbuffers tensorflow-lite \
"

PACKAGECONFIG[tensorflow-lite-delegate] = " \
    -DBUILD_ARMNN_TFLITE_DELEGATE=1, \
    -DBUILD_ARMNN_TFLITE_DELEGATE=0, \
    tensorflow-lite \
"

PACKAGECONFIG[unit-tests] = " \
    -DBUILD_UNIT_TESTS=1, \
    -DBUILD_UNIT_TESTS=0 \
"

PACKAGECONFIG[tests] = " \
    -DBUILD_TESTS=1, \
    -DBUILD_TESTS=0 \
"

PACKAGECONFIG[ref] = " \
    -DARMNNREF=1, \
    -DARMNNREF=0 \
"

EXTRA_OECMAKE += " \
    -DSHARED_BOOST=1 \
    -DHALF_INCLUDE=${STAGING_DIR_HOST} \
"

ARMNN_INSTALL_DIR = "${bindir}/${P}"

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    if ${@bb.utils.contains('PACKAGECONFIG', 'tests', 'true', 'false', d)}; then
        install -d ${D}${ARMNN_INSTALL_DIR}
        find ${WORKDIR}/build/tests -maxdepth 1 -type f -executable -exec cp $CP_ARGS {} ${D}${ARMNN_INSTALL_DIR} \;
        chrpath -d ${D}${ARMNN_INSTALL_DIR}/*
    fi

    if ${@bb.utils.contains('PACKAGECONFIG', 'unit-tests', 'true', 'false', d)}; then
        install -d ${D}${ARMNN_INSTALL_DIR}
        cp $CP_ARGS ${B}/UnitTests ${D}${ARMNN_INSTALL_DIR}
    fi

    if ${@bb.utils.contains('PACKAGECONFIG', 'tensorflow-lite-delegate', 'true', 'false', d)}; then
        for lib in ${WORKDIR}/build/delegate/*.so
        do
            cp $CP_ARGS $lib ${D}${libdir}
        done

        # install delegate headers
        install -d ${D}${includedir}/armnn/delegate
        cd "${S}/delegate/include"
        for file in $(find . -name '*.h*'); do
            cp $CP_ARGS "${file}" "${D}${includedir}/armnn/delegate"
        done

        # install header files
        install -d ${D}${includedir}/profiling
        cd ${S}/profiling
        for h in $(find . -name "*.h*"); do
            [ -d ${D}${includedir}/armnn/profiling/$(dirname $h) ] || install -d ${D}${includedir}/armnn/profiling/$(dirname $h)
            install -m 0644 $h ${D}${includedir}/armnn/profiling/$(dirname $h)
        done
        cd -

        #install delegate cmake module
        install -d ${D}${libdir}
        install -d ${D}${libdir}/cmake
        install -d ${D}${libdir}/cmake/armnn
        cp $CP_ARGS "${B}/delegate/ArmnnDelegateConfig.cmake" "${D}${libdir}/cmake/armnn"
        cp $CP_ARGS "${B}/delegate/CMakeFiles/Export/${baselib}/ArmnnDelegateTargets.cmake" "${D}${libdir}/cmake/armnn"
        cp $CP_ARGS "${B}/delegate/CMakeFiles/Export/${baselib}/ArmnnDelegateTargets-release.cmake" "${D}${libdir}/cmake/armnn"

        #remove duplicate files
        rm ${D}${libdir}/ArmnnDelegate*.cmake

        #install pkgconfig
        install -d ${D}${libdir}/pkgconfig
        install -m 0644 ${UNPACKDIR}/armnn-tflite.pc.in ${D}${libdir}/pkgconfig/armnn-tflite.pc
        sed -i 's:@version@:${PV}:g
            s:@libdir@:${libdir}:g
            s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/armnn-tflite.pc

        install -m 0644 ${UNPACKDIR}/armnn-delegate.pc.in ${D}${libdir}/pkgconfig/armnn-delegate.pc
        sed -i 's:@version@:${PV}:g
            s:@libdir@:${libdir}:g
            s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/armnn-delegate.pc
    fi


}

CXXFLAGS += "-fopenmp -I${STAGING_DIR_HOST}/usr"

CXXFLAGS += "-Wno-error=array-bounds -Wno-error=deprecated-declarations"

LIBS += "-larmpl_lp64_mp"

FILES:${PN}-dev = ""

INSANE_SKIP:${PN} = "dev-so"
INSANE_SKIP:${PN}-dev += "dev-elf"
FILES:${PN} += "${libdir}/*"
FILES:${PN}-dev += "${includedir}/* ${libdir}/cmake/armnn/* ${libdir}/pkgconfig/*.pc ${bindir}/*"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895261
# ERROR: QA Issue: File /usr/lib/cmake/armnn/ArmnnDelegateTargets.cmake in package armnn-dev contains reference to TMPDIR
# File /usr/lib/cmake/armnn/ArmnnTargets.cmake in package armnn-dev contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
