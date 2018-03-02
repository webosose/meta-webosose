# Copyright (c) 2016 LG Electronics, Inc.

DESCRIPTION = "Google Protocol Buffers"
HOMEPAGE = "https://github.com/google/protobuf"
LICENSE = "BSD-3-Clause"

# Note: we are using v2.5.0 and not the latest version of protobuf
# in meta-oe because we currently are using the pre-built binary for Widevine
# libwv_shared.so, that depends on this version of protobuf
# The plan is to compile libwv_shared.so binary from sources when we move from
# the current Widevine kit 2.2 to the newer kit version 3.4

DEPENDS = "zlib"

LIC_FILES_CHKSUM = "file://COPYING.txt;md5=af6809583bfde9a31595a58bb4a24514"

SRCREV = "774d630bde574f5fcbb6dae6eaa0f91f7bc12967"

PV = "2.5.0+git${SRCPV}"

SRC_URI = "git://github.com/google/protobuf.git"

EXTRA_OECONF = "--with-protoc=echo"

inherit autotools

S = "${WORKDIR}/git"

BBCLASSEXTEND = "native nativesdk"
