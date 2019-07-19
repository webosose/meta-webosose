# Copyright (c) 2011-2019 LG Electronics, Inc.

SUMMARY = "Kernel logging daemon"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "glib-2.0"

WEBOS_VERSION = "2.0.0-1_53d5415463435429b5377f0ce76f528c870d7c2e"
PR = "r3"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# http://caprica.lgsvl.com:8080/Errors/Details/1092094
# Configured/src/PmKLogDaemon.c:293:6: error: format not a string literal and no format arguments [-Werror=format-security]
#      fprintf(fp, gOutBuff+counter);
#      ^~~~~~~
SECURITY_STRINGFORMAT = ""
