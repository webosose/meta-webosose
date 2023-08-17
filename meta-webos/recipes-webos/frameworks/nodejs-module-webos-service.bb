# Copyright (c) 2013-2023 LG Electronics, Inc.

SUMMARY = "Loadable Node.js module for webOS services"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webOS/frameworks"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=fd2307a91bcd8a2af8959dc45ad202f7 \
"

WEBOS_VERSION = "1.0.1-7_a514543927ec3998a054ead7654cea6dfa5dee90"
PR = "r5"

inherit webos_enhanced_submissions
inherit webos_system_bus
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${libdir}/node_modules
    install -d ${D}${libdir}/node_modules/webos-service
    install -d ${D}${webos_sysbus_prvservicesdir}
    install -d ${D}${webos_sysbus_pubservicesdir}
    install -d ${D}${webos_sysbus_prvrolesdir}
    install -d ${D}${webos_sysbus_pubrolesdir}
    install -d ${D}${webos_servicesdir}
    for SUB in package.json lib ; do
        cp -R --no-dereference --preserve=mode,links -v $SUB ${D}${libdir}/node_modules/webos-service
    done
    for SUB in sample/* ; do
        cp -R --no-dereference --preserve=mode,links -v $SUB ${D}${webos_servicesdir}
    done
    # remove test and jasminetest dirs
    rm -vrf ${D}${libdir}/node_modules/webos-service/*test
}

PACKAGES =+ "${PN}-samples"
FILES:${PN}-samples += "${webos_servicesdir}"
FILES:${PN}-samples += "${webos_sysbus_prvservicesdir}"
FILES:${PN}-samples += "${webos_sysbus_pubservicesdir}"
FILES:${PN}-samples += "${webos_sysbus_prvrolesdir}"
FILES:${PN}-samples += "${webos_sysbus_pubrolesdir}"
FILES:${PN}-samples += "${webos_sysbus_manifestsdir}"
FILES:${PN} += "${libdir}/node_modules/webos-service/package.json"
FILES:${PN} += "${libdir}/node_modules/webos-service/lib"
