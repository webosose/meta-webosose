# Copyright (c) 2015-2018 LG Electronics, Inc.

SUMMARY = "webOS LS2 security configuration"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.0.2-1_1304f4377720e076a252cf4d24c098647db048ec"
PR = "r2"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES_${PN} += "${webos_sysbus_datadir}"

# The security configuration data isn't needed to build other components => don't stage it.
sysroot_stage_dirs_append() {
    # ${to} is the 2nd parameter passed to sysroot_stage_dir(),
    # e.g. ${SYSROOT_DESTDIR} passed from sysroot_stage_all()
    rm -vrf ${to}${webos_sysbus_datadir}
}
