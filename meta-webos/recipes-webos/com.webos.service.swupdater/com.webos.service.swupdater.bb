# Copyright (c) 2019 LG Electronics, Inc.

DESCRIPTION = "webOS update agent"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/daemon"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "curl glib-2.0 libpbnjson luna-service2 pmloglib ostree"

PR = "r0"

inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus

require swupdater.inc

S = "${WORKDIR}/git"

EXTRA_OECMAKE = "\
    -DLIBOSTREE=ON \
    -DHAWKBIT_ADDRESS=http://10.178.84.116:8080 \
    -DHAWKBIT_TOKEN=377b83e10b9f894883e98351875151cb \
"
