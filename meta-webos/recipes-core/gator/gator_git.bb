# Imported from
# https://github.com/kratsg/meta-l1calo/blob/master/recipes-kernel/gator/gator_git.bb

require gator.inc

SUMMARY = "DS-5 Gator daemon"
DESCRIPTION = "Target-side daemon gathering data for ARM Streamline Performance Analyzer."

PR = "${INC_PR}.5"

# Fails to build with 5.10 kernel
# http://gecko.lge.com:8000/Errors/Details/456083
# RDEPENDS:${PN} += "kernel-module-gator"
# whole timespec use was removed in 7.2 version with:
# Gatord 7.2 for Arm DS 2020.0 and Arm Mobile Studio 2020.0
# https://github.com/ARM-software/gator/commit/fc2b7126b15caf818f377dae911af0a3c29c65f6

SRC_URI += " \
    file://0001-disable-stripping-debug-info.patch \
"

S = "${WORKDIR}/git/daemon"

INHIBIT_PACKAGE_STRIP  = "1"

do_compile() {
    # The regular makefile tries to be 'smart' by hardcoding ABI assumptions, let's use the clean makefile for everything.
    cp ${S}/Makefile_aarch64 ${S}/Makefile
    oe_runmake CROSS_COMPILE="${TARGET_PREFIX}" CC='${CC}' CXX='${CXX}'
}

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${S}/gatord ${D}${sbindir}/gatord
}

FILES:${PN} = " \
    ${sbindir}/gatord \
"
