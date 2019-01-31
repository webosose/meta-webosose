# Copyright (c) 2014-2019 LG Electronics, Inc.

SUMMARY = "Systemd service files for system services"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0 & MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

VIRTUAL-RUNTIME_rdx-utils ?= "rdxd"
RDEPENDS_${PN} = "${VIRTUAL-RUNTIME_init_manager} ${VIRTUAL-RUNTIME_rdx-utils}"
RPROVIDES_${PN} = "initscripts initscripts-functions"
PROVIDES = "initscripts"

# TODO: systemd dependency is for fake initctl.
# The dependency needs to be deleted after deleting fake initctl.
DEPENDS = "systemd"

WEBOS_VERSION = "3.0.0-18_b2c3380a1c761a699fd3a626d6c0c56833dbe40a"
PR = "r11"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_distro_dep
inherit webos_distro_variant_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
