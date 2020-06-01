# Copyright (c) 2013-2020 LG Electronics, Inc.

SUMMARY = "webOS localization tool"
AUTHOR = "Seonmi Jin<seonmi1.jin@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.0.0-3_3a6b4a3e827a1d5eb0cdded285367e7c081b7448"

PR = "r0"

inherit webos_enhanced_submissions
inherit native
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# Skip the unwanted tasks
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${S}/buildloc ${D}${bindir}/buildloc
    install -m 755 ${S}/buildxliff2 ${D}${bindir}/buildxliff2

    install -d ${D}${sysconfdir}/novaloc
    install -m 644 ${S}/xliff.dtd ${D}${sysconfdir}/novaloc
    install -m 644 ${S}/*.properties ${D}${sysconfdir}/novaloc
    install -m 644 ${S}/*.xml ${D}${sysconfdir}/novaloc

    install -d ${D}${sysconfdir}/novaloc/pseudomaps
    install -m 644 ${S}/pseudomaps/*.json ${D}${sysconfdir}/novaloc/pseudomaps

    install -d ${D}${sysconfdir}/novaloc/schema
    install -m 644 ${S}/schema/*.json ${D}${sysconfdir}/novaloc/schema

    install -d ${D}${libdir}/novaloc
    install -m 644 ${S}/*.jar ${D}${libdir}/novaloc
}

sysroot_stage_all_append() {
    # some of our files do not get staged by default so we must force them to be staged
    sysroot_stage_dir ${D}${sysconfdir}/novaloc ${SYSROOT_DESTDIR}${sysconfdir}/novaloc
    sysroot_stage_dir ${D}${libdir}/novaloc ${SYSROOT_DESTDIR}${libdir}/novaloc
}
