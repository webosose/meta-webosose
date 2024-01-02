# Copyright (c) 2019-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

NATIVESDK_PKGGRP_HOST_FOR_WAYLAND = "\
     nativesdk-qtwayland-tools \
     nativesdk-wayland-dev \
"

NATIVESDK_PKGGRP_HOST_FOR_RPM = "\
    nativesdk-rpm \
    nativesdk-rpm-build \
"

NATIVESDK_PKGGRP_HOST_FOR_SDK = "\
    nativesdk-dwarfsrcfiles \
    nativesdk-opkg-utils \
"

RDEPENDS:${PN} += " \
    ${NATIVESDK_PKGGRP_HOST_FOR_WAYLAND} \
    ${NATIVESDK_PKGGRP_HOST_FOR_RPM} \
    ${NATIVESDK_PKGGRP_HOST_FOR_SDK} \
"
