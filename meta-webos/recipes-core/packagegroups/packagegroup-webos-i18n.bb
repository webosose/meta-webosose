# Copyright (c) 2023-2025 LG Electronics, Inc.

DESCRIPTION = "Components for i18n added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r1"

inherit packagegroup
inherit features_check

REQUIRED_DISTRO_FEATURES = "webos-i18n"

# This packageset controls which time zone packages should be included in webOS.
# Since any application that uses localtime will indirectly depend on presence of
# time zone data, we pull in those packages as a top-level dependency. By
# assigning the list to its own variable, we have the option to only include a
# subset should there be a device that will only be used within some region.
WEBOS_PACKAGESET_TZDATA ?= " \
    tzdata \
    tzdata-core \
    tzdata-africa \
    tzdata-americas \
    tzdata-antarctica \
    tzdata-arctic \
    tzdata-asia \
    tzdata-atlantic \
    tzdata-australia \
    tzdata-europe \
    tzdata-misc \
    tzdata-pacific \
    tzdata-posix \
    tzdata-right \
"

RDEPENDS:${PN} = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-qt', 'ilib-qml-plugin', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webos-graphics', 'ilib-webapp', '', d)} \
    ${WEBOS_PACKAGESET_TZDATA} \
"
