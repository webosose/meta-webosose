# Copyright (c) 2014-2025 LG Electronics, Inc.

SUMMARY = "Systemd service files for system services"
AUTHOR = "Sukil Hong <sukil.hong@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0 & MIT"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

# TODO: systemd dependency is for fake initctl.
# The dependency needs to be deleted after deleting fake initctl.
DEPENDS = "systemd"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN} = "${VIRTUAL-RUNTIME_init_manager} ${VIRTUAL-RUNTIME_bash} python3"

PROVIDES = "initscripts"
RPROVIDES:${PN} = "initscripts initd-functions"

WEBOS_VERSION = "3.0.0-102_3ac4a454a79375f9bfaf97a8964de04ef90089ac"
PR = "r20"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-Add-var-cache-xdg-directory-to-tmpfiles-configuratio.patch  \
"
S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DWEBOS_QTTESTABILITY_ENABLED:BOOL=${@ '1' if d.getVar('WEBOS_DISTRO_PRERELEASE') != '' else '0'}"

FILES:${PN} += "${base_libdir}"
