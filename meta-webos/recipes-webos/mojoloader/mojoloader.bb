# Copyright (c) 2012-2018 LG Electronics, Inc.

SUMMARY = "JavaScript loader for foundation frameworks and other loadable libraries"
AUTHOR = "Suresh Arumugam <suresh.arumugam@lge.com>"
SECTION = "webos/frameworks"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.1-1_f84f3db7439cf59cbd53d3e835a98c703709081a"
PR = "r5"

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

FILES_${PN} += "${webos_frameworksdir}"
