# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

NATIVESDK_PKGGRP_HOST_FOR_WAYLAND = "\
     nativesdk-qtwayland-tools \
     nativesdk-wayland-dev \
"

RDEPENDS_${PN} += " \
    ${NATIVESDK_PKGGRP_HOST_FOR_WAYLAND} \
"
