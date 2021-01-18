# Copyright (c) 2019-2021 LG Electronics, Inc.

DESCRIPTION = "webOS update agent"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/daemon"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2714381d01eb5a6e963e62a212e277be \
"

DEPENDS = "curl glib-2.0 libpbnjson luna-service2 pmloglib"

WEBOS_VERSION = "1.0.0-23_e00bf9b9fb23241b0f4dce06ca5a787932f04bee"
PR = "r3"

inherit webos_cmake
inherit webos_component
inherit webos_daemon
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

PACKAGECONFIG ??= "ostree"
PACKAGECONFIG[ostree] = "-DLIBOSTREE=ON,-DLIBOSTREE=OFF,ostree"

EXTRA_OECMAKE = "\
    -DHAWKBIT_ADDRESS=http://10.178.84.116:8080 \
    -DHAWKBIT_TOKEN=377b83e10b9f894883e98351875151cb \
"
