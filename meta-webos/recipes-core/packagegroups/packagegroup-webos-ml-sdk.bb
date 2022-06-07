# Copyright (c) 2022 LG Electronics, Inc.

DESCRIPTION = "Machine learning dev components used in webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS \${PN} variable.
PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep
inherit webos_prerelease_dep

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

RDEPENDS:${PN} = " \
    flatbuffers-dev \
    tensorflow-lite-dev \
    opencl-icd-loader-dev \
    msgpack-c-dev \
    rapidjson-dev \
    jsoncpp-dev \
    edgeai-vision-dev \
    ${@bb.utils.contains('DISTRO_FEATURES', 'armnn', 'arm-compute-library-dev', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'armnn', 'armnn-dev', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'edgetpu', 'libedgetpu-dev', '', d)} \
    googletest-dev \
    ${OPENCV4} \
    ${QT} \
"

