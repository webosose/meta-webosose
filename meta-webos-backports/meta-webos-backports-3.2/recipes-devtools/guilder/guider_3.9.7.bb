# backported as-is from:
# https://git.openembedded.org/meta-openembedded/commit/?id=93c9a20bf358bc10c2d99fc1d3c7247145344c29

SUMMARY = "runtime performance analyzer"
HOMEPAGE = "https://github.com/iipeace/guider"
BUGTRACKER = "https://github.com/iipeace/guider/issues"
AUTHOR = "Peace Lee <ipeace5@gmail.com>"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2c1c00f9d3ed9e24fa69b932b7e7aff2"

PV = "3.9.7+git${SRCPV}"

SRC_URI = "git://github.com/iipeace/${BPN};branch=master;protocol=https"
SRCREV = "459b5189a46023fc98e19888b196bdc2674022fd"

S = "${WORKDIR}/git"

inherit setuptools3

RDEPENDS:${PN} = "python3 python3-core \
        python3-ctypes python3-shell python3-json"
