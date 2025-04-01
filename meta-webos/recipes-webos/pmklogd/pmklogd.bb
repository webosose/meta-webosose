# Copyright (c) 2011-2025 LG Electronics, Inc.

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
PR = "r7"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "pm-klog-daemon.service"

# http://caprica.lgsvl.com:8080/Errors/Details/1092094
# Configured/src/PmKLogDaemon.c:293:6: error: format not a string literal and no format arguments [-Werror=format-security]
#      fprintf(fp, gOutBuff+counter);
#      ^~~~~~~
SECURITY_STRINGFORMAT = ""

# http://gecko.lge.com:8000/Errors/Details/821713
# pmklogd/2.0.0-2/build/Configured/src/PmKLogDaemon.c:274:30: error: implicit declaration of function 'g_strstr_len' [-Wimplicit-function-declaration]
# pmklogd/2.0.0-2/build/Configured/src/PmKLogDaemon.c:298:28: error: implicit declaration of function 'g_str_has_prefix' [-Wimplicit-function-declaration]
CFLAGS += "-Wno-error=implicit-function-declaration"
