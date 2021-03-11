# Copyright (c) 2013-2021 LG Electronics, Inc.

SUMMARY = "Booting sequence and CPU,I/O usage monitor"
DESCRIPTION = "Monitors where the system spends its time at start, creating a graph of all processes, disk utilization, and wait time."
AUTHOR = "Wonhong Kwon <wonhong.kwon@lge.com>"
HOMEPAGE = "https://github.com/xrmx/bootchart"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=44ac4678311254db62edf8fd39cb8124"

RDEPENDS_${PN} = "python3-core python3-pycairo python3-compression python3-image python3-shell python3-codecs python3-misc"

PR = "r11"

inherit autotools-brokensep
inherit systemd

RCONFLICTS_${PN} = "bootchart"

SYSTEMD_SERVICE_${PN} = "bootchart2.service bootchart2-done.service bootchart2-done.timer"

SRCREV = "42509aa0c9c20baa631062496b281f67b31abbd0"
PV = "0.14.8+git${SRCPV}"

SRC_URI = "git://github.com/xrmx/bootchart \
    file://fix-wrong-ppid-tracking-bug.patch \
    file://update-cmds-of-initctls-to-upstart-event-name.patch \
    file://change-a-color-of-upstart-event-name.patch \
    file://fix-bootstartd-index-out-of-bounds.patch \
"

S = "${WORKDIR}/git"

FILES_${PN} += " \
    ${base_libdir}/bootchart/bootchart-collector \
    ${base_libdir}/bootchart/tmpfs \
    ${libdir} \
"
FILES_${PN}-doc += "${datadir}/docs"

do_install () {
    export PYTHON_DIR=python3.5
    export PY_LIBDIR="${libdir}/${PYTHON_DIR}"
    export BINDIR="${bindir}"
    export DESTDIR="${D}"
    export LIBDIR="${base_libdir}"

    oe_runmake install

    # Use python 3 instead of python 2
    sed -i -e '1s,#!.*python.*,#!${bindir}/python3,' ${D}${bindir}/pybootchartgui
}
