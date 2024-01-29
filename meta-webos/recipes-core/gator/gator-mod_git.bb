# Imported from
# https://github.com/kratsg/meta-l1calo/blob/master/recipes-kernel/gator/gator_git.bb

require gator.inc

SUMMARY = "DS-5 Gator Kernel Module"
DESCRIPTION = "Target-side kernel module gathering data for ARM Streamline Performance Analyzer."

PR = "${INC_PR}.2"

SRC_URI += " \
    file://0001-gator-modified-driver-Makefile-to-allow-compilation.patch;patchdir=.. \
    file://0002-gator_main.c-gator_backtrace.c-fix-build-with-linux-.patch;patchdir=.. \
    file://0003-gator-Make-the-module-compatilble-with-kernel-4.19.patch;patchdir=.. \
    file://0004-mali-ignore-events-from-mali.patch;patchdir=.. \
    file://0005-Try-to-fix-build-with-newer-kernel-5.10.patch;patchdir=.. \
    file://0006-gator_main.c-Use-add_cpu-introduced-in-kernel-5.7.patch;patchdir=.. \
    file://0007-gator_events_meminfo-backport-fix-from-6.8.patch;patchdir=.. \
    file://0008-gator_backtrace-check-CONFIG_ARCH_STACKWALK.patch;patchdir=.. \
    file://0009-gator_trace_sched-use-task_is_running-where-possible.patch;patchdir=.. \
    file://0010-gator_annotate-gator_events_perf_pmu-use-pr_warn-ins.patch;patchdir=.. \
    file://0011-gator_cookies.c-use-mas_walk-instead-of-vma-vm_next.patch;patchdir=.. \
    file://0012-gator_cookies.c-update-get_user_pages_remote-macro-f.patch;patchdir=.. \
    file://0013-gator_events_meminfo.c-use-NR_SLAB_RECLAIMABLE_B-NR_.patch;patchdir=.. \
    file://0014-gator_events_meminfo.c-use-DEFINE_SEMAPHORE-with-2-a.patch;patchdir=.. \
    file://0015-gator_pmu-fix-Waddress.patch;patchdir=.. \
    file://0016-Makefile-ignore-incompatible-pointer-types.patch;patchdir=.. \
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
