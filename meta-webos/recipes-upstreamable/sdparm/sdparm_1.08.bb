# Copyright (c) 2013-2017 LG Electronics, Inc.

SUMMARY = "The sdparm utility accesses SCSI device parameters"
SECTION = "console/utils"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=ecab6c36b7ba82c675581dd0afde36f7"

DEPENDS = "sg3-utils"

PR = "r3"

SRC_URI = "http://sg.danny.cz/sg/p/${BP}.tgz \
    file://01_memory_leak.patch"

SRC_URI[md5sum] = "be5786f37499018ef44f409597c92d42"
SRC_URI[sha256sum] = "376b78a414b1a9c47f3f13dbeb963e7a3ec7be126f83927d6856b5f7ac425e57"

inherit gettext autotools

do_configure_prepend() {
    sed -i "s#INCLUDES = -I/usr/include/scsi#INCLUDES = -I${STAGING_INCDIR}/scsi#g" ${S}/src/Makefile.am
}

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
