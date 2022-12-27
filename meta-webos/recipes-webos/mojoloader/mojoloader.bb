# Copyright (c) 2012-2023 LG Electronics, Inc.

SUMMARY = "JavaScript loader for foundation frameworks and other loadable libraries"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/frameworks"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=3603ec4c4d5216855fff467ef39c1bb4 \
"

WEBOS_VERSION = "1.1-3_7c22039cbef6e78adc0b4165a0f6c7232eac24d8"
PR = "r7"

inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_arch_indep
inherit webos_filesystem_paths

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${webos_frameworksdir}
    install -v -m 0644  ${S}/mojoloader.js ${D}${webos_frameworksdir}
}

FILES:${PN} += "${webos_frameworksdir}"
