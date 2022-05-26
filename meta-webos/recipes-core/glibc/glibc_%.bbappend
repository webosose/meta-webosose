# Copyright (c) 2014-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos8"

LICENSE += "& BSD-3-Clause"

# Add missing dependency on BSD-3-Clause license for
LICENSE:eglibc-extra-nss = "GPLv2 & LGPLv2.1 & BSD-3-Clause"
LICENSE:${PN}-dbg = "GPLv2 & LGPLv2.1 & BSD-3-Clause"
LICENSE:${PN}-dev = "GPLv2 & LGPLv2.1 & BSD-3-Clause"
LICENSE:${PN} = "GPLv2 & LGPLv2.1 & BSD-3-Clause"
LICENSE:${PN}-staticdev = "GPLv2 & LGPLv2.1 & BSD-3-Clause"
LICENSE:${PN}-utils = "GPLv2 & LGPLv2.1 & BSD-3-Clause"
