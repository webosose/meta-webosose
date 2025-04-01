# Copyright (c) 2014-2025 LG Electronics, Inc.

SUMMARY = "A BSON utility library"
DESCRIPTION = "libbson is a library providing useful routines related to building, parsing, and iterating BSON documents."
HOMEPAGE = "https://github.com/mongodb/libbson"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=3b83ef96387f14655fc854ddc3c6bd57"

SECTION = "libs"

PR = "r2"
PV = "0.98.0+git${SRCPV}"
# corresponds to 0.98.0
SRCREV = "b2d142f48676124e80578b5d491bd9aec50e748d"
SRC_URI = "git://github.com/mongodb/${BPN};branch=master;protocol=https \
    file://0001-Update-CMakelist-file-to-install-lib-correctly.patch \
"

S = "${WORKDIR}/git"

inherit cmake

do_configure:append() {
    find ${S}/src -type f -name "*.[c|h]" | xargs sed -i 's/\([^"|^\/]\)yajl_/\1bson_yajl_/g'
    find ${S}/src -type f -name "*.[c|h]" | xargs sed -i 's/^yajl_/bson_yajl_/g'
}
