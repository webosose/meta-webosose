SUMMARY = "Tensorflow protobuf files - used in ARMNN for Tensorflow network models"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c7e17cca1ef4230861fb7868e96c387e"

SRC_URI = " \
    git://github.com/tensorflow/tensorflow.git;branch=r2.6;name=tensorflow;protocol=https \
    git://github.com/ARM-software/armnn.git;branch=branches/armnn_21_11;name=armnn;destsuffix=git/armnn;protocol=https \
"

# Matches v${PV}
SRCREV_tensorflow = "c2363d6d025981c661f8cbecf4c73ca7fbf38caf"
# Matches v21.11
SRCREV_armnn = "5e9965cae1cc6162649910f423ebd86001fc1931"
SRCREV_FORMAT = "tensorflow"

DEPENDS = " \
    protobuf-native \
    flatbuffers \
"

S = "${WORKDIR}/git"

do_install() {
    # Install TF sources + build artifacts as reuired by ARMNN
    install -d ${D}${datadir}/${BPN}

    # Convert protobuf sources to C sources and install
    ${S}/armnn/scripts/generate_tensorflow_protobuf.sh ${D}${datadir}/${BPN} ${STAGING_DIR_NATIVE}${prefix_native}

    # Install sources as required by ARMNN
    install -d ${D}${datadir}/${BPN}-lite
    for file in ${S}/tensorflow/lite/schema/*
    do
        [ -f $file ] && install -m 0644 $file ${D}${datadir}/${BPN}-lite
    done
}

FILES:${PN} += "${datadir}"

