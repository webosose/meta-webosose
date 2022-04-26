# Copyright (c) 2011-2022 LG Electronics, Inc.

SUMMARY = "Kernel logging daemon"
AUTHOR = "Anatolii Sakhnik <anatolii.sakhnik@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0"

WEBOS_VERSION = "2.0.0-2_62a67a5fdce9918eda41a2f4479a2c97307bceec"
PR = "r4"

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
