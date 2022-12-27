# Copyright (c) 2021-2023 LG Electronics, Inc.

SUMMARY = "Google Drive for SAF"
AUTHOR = "Navnit Kumar <navnit.kumar@lge.com>"
SECTION = "webos/base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=82938594632d748d209a71bb60e3cdcb \
    file://jconer/LICENSE;md5=82938594632d748d209a71bb60e3cdcb \
"

DEPENDS = "curl"

PV = "0.0.1+git${SRCPV}"
PR = "r3"

SRCREV_FORMAT = "libgdrive"
SRCREV_libgdrive = "a7838bcf55fbbc6cfb66209bb2db1fbe82758924"
SRCREV_common = "d4276cb79d2bccb47431d530e6e3738df49cabd2"
SRCREV_coner = "22dd8e36e96bf38d35896add347a733ad9ed541c"
SRC_URI = "git://github.com/allenbo/libgdrive.git;name=libgdrive;branch=master;protocol=https \
    git://github.com/allenbo/common.git;name=common;destsuffix=git/common;branch=master;protocol=https \
    git://github.com/allenbo/JConer.git;name=coner;destsuffix=git/jconer;branch=master;protocol=https \
    file://0001-patch-for-jconer-in-SAF.patch;patchdir=jconer \
    file://0001-patch-for-libgdrive-in-SAF.patch \
"

S = "${WORKDIR}/git"

#To Build Libgdrive
do_compile:prepend() {
    cp -R ${S}/common/include/common ${S}/jconer/include/
    cd ${S}/jconer
    oe_runmake
    cp -R ${S}/jconer/include/jconer ${S}/include/
    rm -rfv ${S}/lib
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
