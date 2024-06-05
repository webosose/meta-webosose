# Copyright (c) 2012-2024 LG Electronics, Inc.

SUMMARY = "webOS logging daemon"
AUTHOR = "Sukil Hong <sukil.hong@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "pmloglib zlib glib-2.0 libpbnjson pmloglib-private luna-service2"
# show_disk_usage.sh script uses mktemp, find, xargs, and du, all of which are
# provided by busybox.
RDEPENDS:${PN} = "busybox"

WEBOS_VERSION = "3.1.0-15_4b4e74c08a7aa02d4eee9ec94d4459a1ca640c77"
PR = "r12"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_pmlog_config

PACKAGECONFIG ??= ""
PACKAGECONFIG[whitelist] = "-DENABLE_WHITELIST:BOOL=TRUE, -DENABLE_WHITELIST:BOOL=FALSE"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    ${@bb.utils.contains('PACKAGECONFIG', 'whitelist', 'file://whitelist.txt', '', d)} \
"
S = "${WORKDIR}/git"

do_install:append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'whitelist', 'true', 'false', d)} ; then
        install -m 644 ${WORKDIR}/whitelist.txt ${D}${sysconfdir}/PmLogDaemon
    fi
}
FILES:${PN} += "${datadir}/PmLogDaemon"

# http://gecko.lge.com:8000/Errors/Details/821710
# pmlogdaemon/3.1.0-14/git/src/main.c:685:14: error: implicit declaration of function 'open'; did you mean 'popen'? [-Wimplicit-function-declaration]
# pmlogdaemon/3.1.0-14/git/src/main.c:698:18: error: implicit declaration of function 'fcntl' [-Wimplicit-function-declaration]
# pmlogdaemon/3.1.0-14/git/src/main.c:2484:10: error: implicit declaration of function 'inotify_init' [-Wimplicit-function-declaration]
# pmlogdaemon/3.1.0-14/git/src/main.c:2492:10: error: implicit declaration of function 'inotify_add_watch'; did you mean 'g_io_add_watch'? [-Wimplicit-function-declaration]
# pmlogdaemon/3.1.0-14/git/src/main.c:2532:5: error: implicit declaration of function 'inotify_rm_watch' [-Wimplicit-function-declaration]
CFLAGS += "-Wno-error=implicit-function-declaration"
