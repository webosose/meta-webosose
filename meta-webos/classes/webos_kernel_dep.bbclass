# Copyright (c) 2012-2017 LG Electronics, Inc.
#
# webos_kernel_dep
#
# The recipe for every component that needs the non-generic kernel headers must
# inherit this file.
#

# XXX Append -I${STAGING_KERNEL_DIR}/include to CFLAGS

WEBOS_KERNEL_DEPENDS = "virtual/kernel"
DEPENDS:append = " ${WEBOS_KERNEL_DEPENDS}"

do_configure[depends] += "virtual/kernel:do_shared_workdir"
