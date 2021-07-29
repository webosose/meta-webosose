# Copyright (c) 2014-2021 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos7"

LICENSE += "& BSD"

# Add missing dependency on BSD license for
LICENSE:eglibc-extra-nss = "GPLv2 & LGPLv2.1 & BSD"
LICENSE:${PN}-dbg = "GPLv2 & LGPLv2.1 & BSD"
LICENSE:${PN}-dev = "GPLv2 & LGPLv2.1 & BSD"
LICENSE:${PN} = "GPLv2 & LGPLv2.1 & BSD"
LICENSE:${PN}-staticdev = "GPLv2 & LGPLv2.1 & BSD"
LICENSE:${PN}-utils = "GPLv2 & LGPLv2.1 & BSD"
