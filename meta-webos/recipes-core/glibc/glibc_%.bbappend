# Copyright (c) 2014-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos3"

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
