# Copyright (c) 2012-2018 LG Electronics, Inc.

SUMMARY = "webOS logging daemon"
AUTHOR = "Gayathri Srinivasan <gayathri.srinivasan@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "pmloglib zlib glib-2.0 librdx libpbnjson pmloglib-private luna-service2"
# show_disk_usage.sh script uses mktemp, find, xargs, and du, all of which are
# provided by busybox.
RDEPENDS_${PN} = "busybox"

WEBOS_VERSION = "3.1.0-2_3f4e91b95e503b8cd7d5d4207da22fdba026e0c4"
PR = "r9"

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

do_install_append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'whitelist', 'true', 'false', d)} ; then
        install -m 644 ${WORKDIR}/whitelist.txt ${D}${sysconfdir}/PmLogDaemon
    fi
}
FILES_${PN} += "${datadir}/PmLogDaemon"
