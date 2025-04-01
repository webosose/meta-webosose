# Copyright (c) 2022-2025 LG Electronics, Inc.

DESCRIPTION = "Machine learning dev components used in webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r7"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep
inherit webos_prerelease_dep
inherit features_check

REQUIRED_DISTRO_FEATURES = "webos-aiframework"

OPENCV4 = " \
    libopencv-alphamat-dev \
    libopencv-aruco-dev \
    libopencv-barcode-dev \
    libopencv-bgsegm-dev \
    libopencv-bioinspired-dev \
    libopencv-calib3d-dev \
    libopencv-ccalib-dev \
    libopencv-core-dev \
    libopencv-datasets-dev \
    libopencv-dnn-dev \
    libopencv-dnn-objdetect-dev \
    libopencv-dnn-superres-dev \
    libopencv-dpm-dev \
    libopencv-face-dev \
    libopencv-features2d-dev \
    libopencv-flann-dev \
    libopencv-fuzzy-dev \
    libopencv-gapi-dev \
    libopencv-hfs-dev \
    libopencv-highgui-dev \
    libopencv-img-hash-dev \
    libopencv-imgcodecs-dev \
    libopencv-imgproc-dev \
    libopencv-intensity-transform-dev \
    libopencv-line-descriptor-dev \
    libopencv-mcc-dev \
    libopencv-ml-dev \
    libopencv-objdetect-dev \
    libopencv-optflow-dev \
    libopencv-phase-unwrapping-dev \
    libopencv-photo-dev \
    libopencv-plot-dev \
    libopencv-quality-dev \
    libopencv-rapid-dev \
    libopencv-reg-dev \
    libopencv-rgbd-dev \
    libopencv-saliency-dev \
    libopencv-sfm-dev \
    libopencv-shape-dev \
    libopencv-stereo-dev \
    libopencv-stitching-dev \
    libopencv-structured-light-dev \
    libopencv-superres-dev \
    libopencv-surface-matching-dev \
    libopencv-text-dev \
    libopencv-tracking-dev \
    libopencv-ts-dev \
    libopencv-video-dev \
    libopencv-videoio-dev \
    libopencv-videostab-dev \
    libopencv-wechat-qrcode-dev \
    libopencv-xfeatures2d-dev \
    libopencv-ximgproc-dev \
    libopencv-xobjdetect-dev \
    libopencv-xphoto-dev \
    opencv-dev \
"

USE_WAYLAND = " \
    qtwayland-dev \
    qtwayland-plugins \
    qtwayland-tools \
"

QT += " \
    packagegroup-core-standalone-sdk-target \
    qtbase-dev \
    qtbase-plugins \
    qtbase-staticdev \
    qtbase-tools \
    qtdeclarative-dev \
    qtdeclarative-tools \
    qtdeclarative-staticdev \
    qttools-dev \
    qttools-tools \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '${USE_WAYLAND}', '', d)} \
"

USE_ARMNN = " \
    arm-compute-library-dev \
    armnn-dev \
"

USE_EDGETPU = " \
    libedgetpu-dev \
"

USE_XSTACK = " \
    xtl-dev \
    xsimd-dev \
    xtensor-dev \
"

AIFRAMEWORK_CORE = " \
    edgeai-vision-dev \
    flatbuffers-dev \
    googletest-dev \
    msgpack-c-dev \
    msgpack-cpp-dev \
    opencl-icd-loader-dev \
    ${OPENCV4} \
    rapidjson-dev \
    tensorflow-lite-dev \
    xnnpack-dev \
"

AIFRAMEWORK_EXTENDED = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'webos-armnn', '${USE_ARMNN}', '', d)} \
    ${@bb.utils.contains('COMBINED_FEATURES', 'webos-edgetpu', '${USE_EDGETPU}', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'xstack', '${USE_XSTACK}', '', d)} \
"

RDEPENDS:${PN} = " \
    ${AIFRAMEWORK_CORE} \
    ${AIFRAMEWORK_EXTENDED} \
"
