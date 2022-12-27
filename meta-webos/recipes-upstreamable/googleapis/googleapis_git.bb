# Copyright (c) 2018-2023 LG Electronics, Inc.

DESCRIPTION = "Public interface definitions of Google APIs"
AUTHOR = "Kyungjik Min <dp.min@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"
SECTION = "google/api"

PV = "git${SRCPV}"
PR = "r0"

DEPENDS = "grpc-native"

SRC_URI = "git://github.com/googleapis/googleapis.git;branch=master;protocol=https"
SRCREV = "9efcd835514288b21ce37182d75cc75c18b16541"

S = "${WORKDIR}/git"

OUTPUT = "${WORKDIR}/build"
LANGUAGE = "cpp"
GRPCPLUGIN = "${STAGING_BINDIR_NATIVE}/grpc_${LANGUAGE}_plugin"

EXTRA_OEMAKE += "OUTPUT=${OUTPUT} LANGUAGE=${LANGUAGE} GRPCPLUGIN=${GRPCPLUGIN} PROTOINCLUDE=${STAGING_INCDIR_NATIVE}"

do_configure() {
    :
}

do_install() {
    install -d ${D}${includedir}
    cp -R ${OUTPUT}/google ${D}${includedir}/google

    # Remove conflict files between protobuf package
    rm ${D}${includedir}/google/protobuf/struct.pb.h
    rm ${D}${includedir}/google/protobuf/wrappers.pb.h
    rm ${D}${includedir}/google/protobuf/empty.pb.h
    rm ${D}${includedir}/google/protobuf/timestamp.pb.h
    rm ${D}${includedir}/google/protobuf/field_mask.pb.h
    rm ${D}${includedir}/google/protobuf/type.pb.h
    rm ${D}${includedir}/google/protobuf/duration.pb.h
    rm ${D}${includedir}/google/protobuf/descriptor.pb.h
    rm ${D}${includedir}/google/protobuf/source_context.pb.h
    rm ${D}${includedir}/google/protobuf/any.pb.h
    rm ${D}${includedir}/google/protobuf/api.pb.h
    rm ${D}${includedir}/google/protobuf/compiler/plugin.pb.h
}
