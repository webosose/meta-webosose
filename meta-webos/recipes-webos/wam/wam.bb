# Copyright (c) 2015-2018 LG Electronics, Inc.

SUMMARY = "WebAppMgr is responsible for running web applications on webOS"
AUTHOR = "Lokesh Kumar Goel <lokeshkumar.goel@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "chromium53 qtbase luna-service2 sqlite3 librolegen nyx-lib openssl luna-prefs libpbnjson npapi-headers freetype serviceinstaller glib-2.0 pmloglib lttng-ust"
PROVIDES = "webappmanager-webos"

# webappmgr's upstart conf expects to be able to LD_PRELOAD ptmalloc3
RDEPENDS_${PN} = "ptmalloc3"
# webappmgr's upstart conf expects to have ionice available. Under OE-core, this is supplied by util-linux.
RDEPENDS_${PN} += "util-linux"
RDEPENDS_${PN} += "qtdeclarative-plugins qtbase-plugins"

#  webappmgr2's upstart conf expects setcpushares-task to be available
VIRTUAL-RUNTIME_cpushareholder ?= "cpushareholder-stub"
RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_cpushareholder}"

WEBOS_VERSION = "1.0.0-1_2a11010979e1514eee69c2b8954c42931f76049e"
PR = "r16"

inherit webos_enhanced_submissions
inherit webos_system_bus
inherit webos_machine_dep
inherit webos_qmake5
inherit webos_lttng
inherit webos_distro_variant_dep
inherit webos_distro_dep
inherit webos_public_repo

WAM_DATA_DIR = "${webos_execstatedir}/${BPN}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
WEBOS_SYSTEM_BUS_FILES_LOCATION = "${S}/files/sysbus"

OE_QMAKE_PATH_HEADERS = "${OE_QMAKE_PATH_QT_HEADERS}"

WEBOS_QMAKE_TARGET = "${MACHINE}"

# Set the location of chromium headers
EXTRA_QMAKEVARS_PRE += "CHROMIUM_SRC_DIR=${STAGING_INCDIR}/chromium53"

# Enable LTTng tracing capability when enabled in webos_lttng class
EXTRA_QMAKEVARS_PRE += "${@oe.utils.conditional('WEBOS_LTTNG_ENABLED', '1', 'CONFIG+=lttng', '', d)}"

EXTRA_QMAKEVARS_PRE += "DEFINES+=WAM_DATA_DIR=\"\"${webos_cryptofsdir}/.webappmanager/\"\""
EXTRA_QMAKEVARS_PRE += "PREFIX=/usr"
EXTRA_QMAKEVARS_PRE += "PLATFORM=${@'PLATFORM_' + '${DISTRO}'.upper().replace('-', '_')}"

# Set number of raster threads for Blink to 2
WEBAPPMANAGER3_NUM_RASTER_THREADS = "2"

# chromium doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"

do_configure_append() {
    cp -f ${S}/files/launch/WebAppMgr.conf.upstart.in ${S}/files/launch/WebAppMgr.conf
    sed -i "s@WAM_DATA_DIR@${WAM_DATA_DIR}@g" ${S}/files/launch/WebAppMgr-finalize.conf.upstart
    sed -i "s@WEBOS_CRYPTOFSDIR@${webos_cryptofsdir}@g" ${S}/files/launch/WebAppMgr.conf
    sed -i "s@WEBOS_SYSMGR_LOCALSTATEDIR@${webos_sysmgr_localstatedir}@g" ${S}/files/launch/WebAppMgr.conf
    sed -i "s@WEBOS_PREFIX@${webos_prefix}@g" ${S}/files/launch/WebAppMgr.conf
    grep 'export HOOK_SEGV' ${S}/files/launch/WebAppMgr.conf || sed -i '/\/usr\/bin\/WebAppMgr/i\    export HOOK_SEGV=NO'  ${S}/files/launch/WebAppMgr.conf
    sed -i "s@WEBOS_NUM_RASTER_THREADS@${WEBAPPMANAGER3_NUM_RASTER_THREADS}@g" ${S}/files/launch/WebAppMgr.conf
    sed -i '/--disable-low-res-tiling \\/a\        --disable-new-video-renderer \\' ${S}/files/launch/WebAppMgr.conf
    sed -i '/--enable-gpu-rasterization \\/a\        --disable-gpu-rasterization-for-first-frame \\' ${S}/files/launch/WebAppMgr.conf
    cp -f ${S}/files/launch/WebAppMgr.conf ${S}/files/launch/WebAppMgr.conf.upstart
}

do_install_append() {
    install -d ${D}${sysconfdir}/init
    install -d ${D}${sysconfdir}/pmlog.d
    install -d ${D}${sysconfdir}/wam
    install -d ${D}${WAM_DATA_DIR}
    install -v -m 644 ${S}/files/launch/WebAppMgr.conf.upstart ${D}${sysconfdir}/init/WebAppMgr.conf
    install -v -m 644 ${S}/files/launch/security_policy.conf ${D}${sysconfdir}/wam/security_policy.conf
}

FILES_${PN} += "${webos_upstartconfdir} ${sysconfdir}/pmlog.d ${sysconfdir}/init ${sysconfdir}/wam ${libdir}/webappmanager/plugins/*.so"
