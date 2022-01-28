# Imported from
# https://github.com/kratsg/meta-l1calo/blob/master/recipes-kernel/gator/gator_git.bb

require gator.inc

SUMMARY = "DS-5 Gator Kernel Module"
DESCRIPTION = "Target-side kernel module gathering data for ARM Streamline Performance Analyzer."

PR = "${INC_PR}.0"

SRC_URI += " \
    file://0001-gator-mod-makefile-for-yocto.patch \
    file://0002-gator_main.c-gator_backtrace.c-fix-build-with-linux-.patch \
    file://0003-gator-Make-the-module-compatilble-with-kernel-4.19.patch \
    file://Mali_events_disable.patch \
"

S = "${WORKDIR}/git/driver"

inherit module

#The original gator module Makefile generates an include file that contains the md5sum of all source files.
#After modifying the makefile to make it work with the Yocto build system, this doesn't work anymore.
#Generate it in the bitbake recipe instead.
do_compile:prepend() {
    GATOR_SRC_FILES=$(cd ${S} && ls *.c *.h mali/*.h | grep -Ev '^(gator_src_md5\.h|gator\.mod\.c)$$' | LC_ALL=C sort )
    GATOR_SRC_FILES_MD5=$(echo ${GATOR_SRC_FILES} | xargs cat | md5sum | cut -b 1-32 )
    #echo "GATOR_SRC_FILES: ${GATOR_SRC_FILES}"
    #echo "GATOR_SRC_FILES_MD5: ${GATOR_SRC_FILES_MD5}"
    echo "static char *gator_src_md5 = \"${GATOR_SRC_FILES_MD5}\";" > ${S}/generated_gator_src_md5.h
}

INHIBIT_PACKAGE_STRIP = "1"

EXTRA_OEMAKE:append:task-compile = " PYTHON='python3' CONFIG_GATOR=m"
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.
RPROVIDES:${PN} += "kernel-module-gator"

# Load the Kernel Module on Boot as it is required by gatord when started
KERNEL_MODULE_AUTOLOAD += "gator"
