# Copyright (c) 2023-2024 LG Electronics, Inc.

DESCRIPTION = "Components for test apps added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r1"

inherit packagegroup
inherit webos_prerelease_dep
inherit features_check

REQUIRED_DISTRO_FEATURES = "webos-graphics"

WEBOS_PACKAGESET_TESTAPPS = " \
    bareapp \
    com.webos.app.test.enact \
    com.webos.app.test.v8snapshot \
    com.webos.app.test.webosose \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-media', 'com.webos.app.test.youtube', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-camera', 'com.webos.app.test.webrtc', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-media', 'com.webos.app.test.mediacontroller', '', d)} \
"

RDEPENDS:${PN} = " \
    ${@ '' if '${WEBOS_DISTRO_PRERELEASE}' == '' else '${WEBOS_PACKAGESET_TESTAPPS}'} \
"
