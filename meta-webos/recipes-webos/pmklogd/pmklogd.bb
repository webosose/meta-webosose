# Copyright (c) 2011-2023 LG Electronics, Inc.

SUMMARY = "Kernel logging daemon"
AUTHOR = "Sukil Hong <sukil.hong@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0"

WEBOS_VERSION = "2.0.0-2_62a67a5fdce9918eda41a2f4479a2c97307bceec"
PR = "r5"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://pm-klog-daemon.service \
"
S = "${WORKDIR}/git"

# http://caprica.lgsvl.com:8080/Errors/Details/1092094
# Configured/src/PmKLogDaemon.c:293:6: error: format not a string literal and no format arguments [-Werror=format-security]
#      fprintf(fp, gOutBuff+counter);
#      ^~~~~~~
SECURITY_STRINGFORMAT = ""

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system/
    install -v -m 0644 ${WORKDIR}/pm-klog-daemon.service ${D}${sysconfdir}/systemd/system/
}
