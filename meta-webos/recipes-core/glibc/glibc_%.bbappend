# Copyright (c) 2014-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos6"

LICENSE += "& BSD"

# Add missing dependency on BSD license for
LICENSE_eglibc-extra-nss = "GPLv2 & LGPLv2.1 & BSD"
LICENSE_${PN}-dbg = "GPLv2 & LGPLv2.1 & BSD"
LICENSE_${PN}-dev = "GPLv2 & LGPLv2.1 & BSD"
LICENSE_${PN} = "GPLv2 & LGPLv2.1 & BSD"
LICENSE_${PN}-staticdev = "GPLv2 & LGPLv2.1 & BSD"
LICENSE_${PN}-utils = "GPLv2 & LGPLv2.1 & BSD"

do_install_append() {
    # prevent staging libSegFault.so in sysroot, crashd is providing the same file
    # 2 runtime providers are correctly handled by RPROVIDES/RREPLACES/RCONFLICTS combo
    # but populate_sysroot shows warning about staging the same file from multiple recipes.
    # It's harmless in this case, because AFAIK nothing directly links with libSegFault.so.
    rm -f ${D}${base_libdir}/libSegFault.so
}

# don't pull libsegfault into every build with glibc, because we
# use libsegfault-webos which is provided by crashd, but crashd has
# quite a few of dependencies and we don't want to build them in
# each build which contains glibc and catchsegv
RDEPENDS_catchsegv_remove = "libsegfault"
