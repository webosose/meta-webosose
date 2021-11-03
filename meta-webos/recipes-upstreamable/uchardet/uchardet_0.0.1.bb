# Copyright (c) 2013-2021 LG Electronics, Inc.

DESCRIPTION = "uchardet is a C language binding of the original C++ \
implementation of the universal charset detection library by Mozilla."
HOMEPAGE = "https://code.google.com/p/uchardet/"
LICENSE = "MPLv1.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=bfe1f75d606912a4111c90743d6c7325"

PR = "r1"

# there is no tag for 0.0.1 version, but content of this revision matches
# with uchardet-0.0.1.tar.gz
SRCREV = "56a4c0d86c1c89ae3f756480fdec03ba5f7e7323"
SRC_URI = "git://github.com/BYVoid/${BPN}.git;branch=master;protocol=https"
S = "${WORKDIR}/git"

inherit cmake
