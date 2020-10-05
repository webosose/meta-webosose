SUMMARY = "DS-5 Gator daemon"
DESCRIPTION = "Target-side daemon gathering data for ARM Streamline Performance Analyzer."

LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://driver/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "9815e8e8a9e91f181f1a1e19371c3e3d8cacae51"
PV = "6.9+git${SRCPV}"
PR = "r3"

SRC_URI = "git://github.com/ARM-software/gator.git;protocol=http;branch=master \
    file://0001-disable-stripping-debug-info.patch \
    file://0001-gator_main.c-gator_backtrace.c-fix-build-with-linux-.patch \
    file://Mali_events_disable.patch \
"

S = "${WORKDIR}/git"

inherit module

INHIBIT_PACKAGE_STRIP  = "1"

do_compile() {
    unset LDFLAGS
    oe_runmake -C daemon CROSS_COMPILE=${TARGET_PREFIX} CC='${CC}' CXX='${CXX}' LDFLAGS='${TARGET_LDFLAGS}'
    oe_runmake -C ${STAGING_KERNEL_BUILDDIR} ARCH=${ARCH} PYTHON='python3' CONFIG_GATOR=m M=${S}/driver modules
}

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${S}/daemon/gatord  ${D}${sbindir}/gatord
    install -m 0755 ${S}/driver/gator.ko ${D}${sbindir}/gator.ko
}

FILES_${PN} = " \
    ${sbindir}/gatord \
    ${sbindir}/gator.ko \
"
