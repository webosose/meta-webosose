# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "Google Drive for SAF"
AUTHOR = "Navnit Kumar <navnit.kumar@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "curl"

PR = "r1"

inherit webos_component
inherit webos_machine_impl_dep
inherit webos_filesystem_paths

SRCREV_libgdrive = "a7838bcf55fbbc6cfb66209bb2db1fbe82758924"
SRC_URI = "git://github.com/allenbo/libgdrive.git;branch=master;name=libgdrive;destsuffix=${WORKDIR}/git"

SRCREV_common = "d4276cb79d2bccb47431d530e6e3738df49cabd2"
SRC_URI += "git://github.com/allenbo/common.git;branch=master;name=common;destsuffix=${WORKDIR}/git/common"

SRCREV_coner = "22dd8e36e96bf38d35896add347a733ad9ed541c"
SRC_URI += "git://github.com/allenbo/JConer.git;branch=master;name=coner;destsuffix=${WORKDIR}/git/jconer"

SRC_URI += "file://0001-patch-for-jconer-in-SAF.patch;patchdir=${WORKDIR}/git/jconer"
SRC_URI += "file://0001-patch-for-libgdrive-in-SAF.patch"

S = "${WORKDIR}/git"

#To Build Libgdrive
do_compile_prepend() {
    cp -R ${S}/common/include/common ${S}/jconer/include/
    cd ${S}/jconer
    oe_runmake
    cp -R ${S}/jconer/include/jconer ${S}/include/
    mkdir ${S}/lib
    cp ${S}/jconer/libjconer.a ${S}/lib/
    cp -R ${S}/common/include/common ${S}/include/
    cd ${S}
}

do_install() {
    install -d ${D}${libdir}
    install -m 0644 ${S}/lib/libjconer.a  ${D}${libdir}/libjconer.a
    install -m 0644 ${S}/libgdrive.a  ${D}${libdir}/libgdrive.a

    install -d ${D}${includedir}
    cp -rf ${S}/include/common ${D}${includedir}/common/
    cp -rf ${S}/include/gdrive ${D}${includedir}/gdrive/
    cp -rf ${S}/include/jconer ${D}${includedir}/jconer/
}

FILES_${PN} = " \
    ${D}${libdir}/libjconer.a \
    ${D}${libdir}/libgdrive.a \
"
do_package_qa[noexec] = "1"
