# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "webOS Edge AI Computer Vision Library"
DESCRIPTION = "webOS Edge AI Computer Vision Library using TensorflowLite"
SECTION = "libs"
LICENSE = "CLOSED"

WEBOS_VERSION = "1.0.0-7_21bd046126c31a1c503aaad418781bc03832d09a"
WEBOS_REPO_NAME = "edge-ai-computer-vision"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

PR = "r2"
S = "${WORKDIR}/git"

inherit cmake
inherit pkgconfig
inherit webos_filesystem_paths
inherit webos_enhanced_submissions
inherit webos_public_repo

DEPENDS = " \
    msgpack-c \
    rapidjson \
    flatbuffers \
    opencv \
    tensorflow-lite \
"

RDEPENDS:${PN} = " \
    msgpack-c \
    rapidjson \
    opencv \
    tensorflow-lite \
"

PACKAGECONFIG ?= "xnnpack"
PACKAGECONFIG += "${@bb.utils.contains('DISTRO_FEATURES', 'gpu-delegate', 'gpu', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('DISTRO_FEATURES', 'edgetpu', 'edgetpu', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('DISTRO_FEATURES', 'armnn', 'armnn', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('DISTRO_FEATURES', 'ml-library-size-reduction', '', 'examples', d)}"

PACKAGECONFIG[xnnpack] = "-DWITH_XNNPACK:BOOL=TRUE,-DWITH_XNNPACK:BOOL=FALSE"
PACKAGECONFIG[gpu] = "-DWITH_GPU=ON, -DWITH_GPU=OFF"
PACKAGECONFIG[edgetpu] = "-DWITH_EDGETPU:BOOL=TRUE,-DWITH_EDGETPU:BOOL=FALSE,libedgetpu"
PACKAGECONFIG[armnn] = "-DWITH_ARMNN:BOOL=TRUE,-DWITH_ARMNN:BOOL=FALSE,armnn"
PACKAGECONFIG[examples] = "-DBUILD_EXAMPLES=ON,-DBUILD_EXAMPLES=OFF,,"

AIF_INSTALL_DIR = "${datadir}/aif"

do_install() {

    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"
    install -d ${D}${AIF_INSTALL_DIR}

    if ${@bb.utils.contains('PACKAGECONFIG', 'examples', 'true', 'false', d)}; then
        # install examples
        find ${WORKDIR}/build/example -maxdepth 2 -type f -executable -exec cp $CP_ARGS {} ${D}${AIF_INSTALL_DIR} \;
        chrpath -d ${D}${AIF_INSTALL_DIR}/*

        # install images files
        cd "${S}/images"
        for file in $(find . -type f); do
            install -d "${D}${AIF_INSTALL_DIR}/images/$(dirname -- "${file}")"
            cp $CP_ARGS "${file}" "${D}${AIF_INSTALL_DIR}/images/${file}"
        done

        # install extra models
        cd "${S}/extra_models"
        for file in $(find . -type f); do
            install -d "${D}${AIF_INSTALL_DIR}/extra_models/$(dirname -- "${file}")"
            cp $CP_ARGS "${file}" "${D}${AIF_INSTALL_DIR}/extra_models/${file}"
        done
    fi

    # install library files
    install -d ${D}${libdir}
    install -m 0755 ${WORKDIR}/build/framework/*.so ${D}${libdir}

    # install header files
    cd "${S}/include/aif"
    for file in $(find . -name '*.h'); do
        install -d "${D}${includedir}/aif/$(dirname -- "${file}")"
        cp $CP_ARGS "${file}" "${D}${includedir}/aif/${file}"
    done

    # install mandatory model files
    cd "${S}/model"
    for file in $(find . -name '*.tflite'); do
        install -d "${D}${AIF_INSTALL_DIR}/model/$(dirname -- "${file}")"
        cp $CP_ARGS "${file}" "${D}${AIF_INSTALL_DIR}/model/${file}"
    done

    # install pkgconfig file
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/files/edgeai-vision.pc.in ${D}${libdir}/pkgconfig/edgeai-vision.pc
    sed -i 's:@version@:${PV}:g
        s:@libdir@:${libdir}:g
        s:@includedir@:${includedir}:g' ${D}${libdir}/pkgconfig/edgeai-vision.pc

}

FILES:${PN}-dev = ""

INSANE_SKIP:${PN} = "dev-so"
INSANE_SKIP:${PN}-dev += "dev-elf"

FILES:${PN}-dev += "${includedir}/* ${libdir}/pkgconfig"
FILES:${PN} += " \
    ${libdir}/*.so \
    ${AIF_INSTALL_DIR} \
"
