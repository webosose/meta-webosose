# Copyright (c) 2014-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos8"

# Including "& LGPL-2.1-only & GPL-2.0-only" is needed only until it's updated in ustream recipe in kirkstone:
# https://git.openembedded.org/openembedded-core/commit/meta/recipes-core/glibc/glibc-common.inc?id=ceda3238cdbf1beb216ae9ddb242470d5dfc25e0
LICENSE += "& BSD-3-Clause & LGPL-2.1-only & GPL-2.0-only"

# Add missing dependency on BSD-3-Clause license for
LICENSE:eglibc-extra-nss = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
LICENSE:${PN}-dbg = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
LICENSE:${PN}-dev = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
LICENSE:${PN} = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
LICENSE:${PN}-staticdev = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
LICENSE:${PN}-utils = "GPL-2.0-only & LGPL-2.1-only & BSD-3-Clause"
