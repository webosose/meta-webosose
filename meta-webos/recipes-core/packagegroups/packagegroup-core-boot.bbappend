# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

RDEPENDS:${PN} += " \
    connman-client \
    kernel \
    kernel-base \
    kernel-image \
    lsb-release \
    procps \
"
