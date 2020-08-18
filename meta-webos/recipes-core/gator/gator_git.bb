SUMMARY = "DS-5 Gator daemon"
DESCRIPTION = "Target-side daemon gathering data for ARM Streamline Performance Analyzer."

LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://driver/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "3ff46fedd4d097813156069edab9704cc65e0a42"
PV = "6.7+git${SRCPV}"
PR = "r2"

SRC_URI = "git://github.com/ARM-software/gator.git;protocol=http;branch=master \
           file://0001-disable-stripping-debug-info.patch \
           file://0001-gator_main.c-gator_backtrace.c-fix-build-with-linux-.patch \
           file://Mali_events_disable.patch \
           file://git/gcc-wrapper.py \
"

S = "${WORKDIR}/git"

inherit module

INHIBIT_PACKAGE_STRIP  = "1"

do_compile() {
  unset LDFLAGS
  export LDFLAGS=''
  # The regular makefile tries to be 'smart' by hardcoding ABI assumptions, let's use the clean makefile for everything.
  cp ${S}/daemon/Makefile_aarch64 ${S}/daemon/Makefile
  oe_runmake -C daemon CROSS_COMPILE=${TARGET_PREFIX} CC='${CC}' CXX='${CXX}'

  #Build gator.ko
  #copy python3 compatible gcc-wrapper.py to kernel source
  cp ${S}/gcc-wrapper.py ${STAGING_KERNEL_DIR}/scripts/
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
do_package_qa[noexec] = "1"
