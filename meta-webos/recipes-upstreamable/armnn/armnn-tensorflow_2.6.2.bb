SUMMARY = "Tensorflow protobuf files - used in ARMNN for Tensorflow network models"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c7e17cca1ef4230861fb7868e96c387e"

SRC_URI = " \
    git://github.com/tensorflow/tensorflow.git;branch=r2.6;name=tensorflow \
    git://github.com/ARM-software/armnn.git;branch=branches/armnn_21_11;name=armnn;subdir=${WORKDIR}/armnn;destsuffix=armnn \
"

SRCREV_tensorflow = "v${PV}"
SRCREV_armnn = "v21.11"
SRCREV_FORMAT = "tensorflow"

DEPENDS = " \
        protobuf-native \
        flatbuffers \
"

S = "${WORKDIR}/git"

do_install() {
    bbplain "STAGING_DIR_NATIVE is ${STAGING_DIR_NATIVE}"
    bbplain "prefix is ${prefix}"

    # Install TF sources + build artifacts as reuired by ARMNN
    install -d ${D}${datadir}/${BPN}

    # Convert protobuf sources to C sources and install
    ${WORKDIR}/armnn/scripts/generate_tensorflow_protobuf.sh ${D}${datadir}/${BPN} ${STAGING_DIR_NATIVE}${prefix}

    # Install sources as required by ARMNN
    install -d ${D}${datadir}/${BPN}-lite
    for file in ${S}/tensorflow/lite/schema/*
    do
        [ -f $file ] && install -m 0644 $file ${D}${datadir}/${BPN}-lite
    done
}

FILES:${PN} += "${datadir}"

