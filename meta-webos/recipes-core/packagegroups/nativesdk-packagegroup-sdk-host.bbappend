# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

NATIVESDK_PKGGRP_HOST_FOR_WAYLAND = "\
     nativesdk-qtwayland-tools \
     nativesdk-wayland-dev \
"

RDEPENDS:${PN} += " \
    ${NATIVESDK_PKGGRP_HOST_FOR_WAYLAND} \
"
