# Copyright (c) 2015-2016 LG Electronics, Inc.

SUMMARY = "Simple top-like I/O monitor"
DESCRIPTION = "iotop does for I/O usage what top(1) does for CPU usage. \
    It watches I/O usage information output by the Linux kernel and displays \
    a table of current I/O usage by processes on the system."
HOMEPAGE = "http://guichaz.free.fr/iotop/"

PR = "r3"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4325afd396febcb659c36b49533135d4"

SRCREV = "59e2537794d91c1375d391293f8fd89ca8b794a8"
SRC_URI = "git://repo.or.cz/iotop.git \
           file://iotop_not_disappear_result_when_exited_thread.patch"

S = "${WORKDIR}/git"

inherit distutils

do_install_append() {
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/site.pyo
    rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/site.py
}

RDEPENDS_${PN} = "python-curses python-textutils \
                  python-codecs python-ctypes python-pprint \
                  python-shell"

# Need to add ${sbindir}/* back to FILES_${PN} because the override of it in
# distutils-common-base.bbclass doesn't include it.
FILES_${PN} += "${sbindir}/*"
