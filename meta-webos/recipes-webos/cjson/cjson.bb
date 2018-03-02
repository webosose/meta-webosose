# Copyright (c) 2012-2018 LG Electronics, Inc.

SUMMARY = "webOS of the open-source json-c library"
AUTHOR = "Keith Derrick <keith.derrick@lge.com>"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit webos_autotools
inherit webos_enhanced_submissions
inherit webos_library
inherit webos_public_repo

WEBOS_VERSION = "1.8.0-1_d07d717a33c4067e75e7f47688add9e28686106d"
PR = "r4"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECONF += "--disable-static"

EXTRA_OEMAKE += "all"

# autotools-brokensep (autogen calls autoreconf in $B)
B = "${S}"
do_configure_prepend() {
    # Force a configure to happen
    rm -f ${S}/config.status
    sh ${S}/autogen.sh
}
