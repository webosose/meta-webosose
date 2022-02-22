# Copyright (c) 2014-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos8"

LICENSE += "& BSD-3-Clause"

# Add missing dependency on BSD-3-Clause license for
LICENSE:eglibc-extra-nss = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
LICENSE:${PN}-dbg = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
LICENSE:${PN}-dev = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
LICENSE:${PN} = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
LICENSE:${PN}-staticdev = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
LICENSE:${PN}-utils = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
