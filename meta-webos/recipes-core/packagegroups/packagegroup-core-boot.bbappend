# Copyright (c) 2023 LG Electronics, Inc.

# You don't need to change this value when you're changing just a RDEPENDS:${PN} variable.
EXTENDPRAUTO:append = "webos1"

RDEPENDS:${PN} += " \
    connman-client \
    kernel \
    kernel-base \
    kernel-image \
    lsb-release \
    procps \
"
