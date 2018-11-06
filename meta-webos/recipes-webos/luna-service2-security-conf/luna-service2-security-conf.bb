# Copyright (c) 2015-2018 LG Electronics, Inc.

SUMMARY = "webOS LS2 security configuration"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.0.2-4_6643661be65d7731306b13d4b27c02a11361c1c5"
PR = "r3"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus

SRC_URI[vardeps] += "PREFERRED_PROVIDER_virtual/webruntime"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

CHR68_PATCHES = "\
  file://0001-Revert-Remove-service-permissions-related-to-wam.patch\
"

SRC_URI_append = "${@oe.utils.conditional('PREFERRED_PROVIDER_virtual/webruntime', 'webruntime', '${CHR68_PATCHES}', '', d)}"

FILES_${PN} += "${webos_sysbus_datadir}"

# The security configuration data isn't needed to build other components => don't stage it.
sysroot_stage_dirs_append() {
    # ${to} is the 2nd parameter passed to sysroot_stage_dir(),
    # e.g. ${SYSROOT_DESTDIR} passed from sysroot_stage_all()
    rm -vrf ${to}${webos_sysbus_datadir}
}
