# Copyright (c) 2015-2024 LG Electronics, Inc.

SUMMARY = "WebAppMgr is responsible for running web applications on webOS"
AUTHOR = "Donghyun Kim <donghyun11.kim@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=790420e31fa17284afec484d5b2ad2d8"

DEPENDS = "virtual/webruntime luna-service2 sqlite3 librolegen nyx-lib openssl luna-prefs libpbnjson freetype serviceinstaller glib-2.0 pmloglib lttng-ust gtest jsoncpp boost"
PROVIDES = "virtual/webappmanager-webos"

# webappmgr's upstart conf expects to be able to LD_PRELOAD ptmalloc3
RDEPENDS:${PN} = "ptmalloc3"
# webappmgr's upstart conf expects to have ionice available. Under OE-core, this is supplied by util-linux.
RDEPENDS:${PN} += "util-linux"

#  webappmgr2's upstart conf expects setcpushares-task to be available
VIRTUAL-RUNTIME_cpushareholder ?= "cpushareholder-stub"
RDEPENDS:${PN} += "${VIRTUAL-RUNTIME_cpushareholder}"

WEBOS_VERSION = "1.0.2-83_9e666f281d73ba1ff7b24d996f199b2343950369"
INC_PR = "r66"

WAM_BUILD_SYSTEM ?= "webos_cmake"
WAM_BUILD_DEFAULT_PLUGIN ?= "1"

inherit webos_enhanced_submissions
inherit webos_system_bus
inherit webos_machine_dep
inherit ${WAM_BUILD_SYSTEM}
inherit webos_pkgconfig
inherit webos_lttng
inherit webos_distro_variant_dep
inherit webos_distro_dep
inherit webos_public_repo
inherit webos_systemd

WAM_DATA_DIR = "${webos_execstatedir}/${BPN}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"

# Enable LTTng tracing capability when enabled in webos_lttng class
EXTRA_OECMAKE += "${@oe.utils.conditional('WEBOS_LTTNG_ENABLED', '1', '-DWEBOS_LTTNG_ENABLED:BOOLEAN=True', '', d)}"

EXTRA_OECMAKE += "-DWAM_DATA_DIR=\"\"${webos_cryptofsdir}/.webappmanager/\"\""
EXTRA_OECMAKE += "-DPLATFORM=${@'PLATFORM_' + '${DISTRO}'.upper().replace('-', '_')}"

EXTRA_OECMAKE += "-DWEBOS_TESTS_DIR=${webos_testsdir}"
EXTRA_OECMAKE += "-DWAM_BUILD_DEFAULT_PLUGIN=${WAM_BUILD_DEFAULT_PLUGIN}"

PACKAGECONFIG:append = " ${@bb.utils.filter('DISTRO_FEATURES', 'smack', d)}"
PACKAGECONFIG[smack] = "-DWEBOS_SMACK_ENABLED:BOOLEAN=True,-DWEBOS_SMACK_ENABLED:BOOLEAN=False"

# chromium doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv6 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"
COMPATIBLE_MACHINE:x86 = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"

WAM_ERROR_SCRIPTS_PATH = "${S}/html-ose"

# Flag to control runtime flags for touch
TOUCH_ENABLED ?= "${@bb.utils.contains('DISTRO_FEATURES', 'webos-touch', 'true', 'false', d)}"

# Flag to control runtime flag for Media Player Neva
DISABLE_NEVA_MEDIA_PLAYER ?= "false"

do_install:append() {
    install -d ${D}${sysconfdir}/pmlog.d
    install -d ${D}${sysconfdir}/wam
    install -d ${D}${WAM_DATA_DIR}
    install -v -m 644 ${S}/files/launch/security_policy.conf ${D}${sysconfdir}/wam/security_policy.conf
    # add loaderror.html and geterror.js to next to resources directory (webos_localization_resources_dir)
    install -d ${D}${datadir}/localization/${BPN}/
    cp -vf ${WAM_ERROR_SCRIPTS_PATH}/* ${D}${datadir}/localization/${BPN}/
}

WEBOS_SYSTEMD_SERVICE = "webapp-mgr.service"
WEBOS_SYSTEMD_SCRIPT = "webapp-mgr.sh.in"

install_units:append() {
    script=${D}${systemd_system_unitdir}/scripts/webapp-mgr.sh

    if [ -f "$script" ]; then
        sed -i -e "s/NETWORK_STABLE_TIMEOUT/NETWORK_QUIET_TIMEOUT/gI" -e "s/network-stable-timeout/network-quiet-timeout/gI" $script
        sed -i '/export WAM_COMMON_SWITCHES=\" \\/a\    --disable-in-process-stack-traces \\' $script
        sed -i '/export ENABLE_BLINK_FEATURES=/ s/$/,LocalResourceCodeCache,CustomEventExtension,WebCodecs,MediaStreamInsertableStreams/' $script
        sed -i -e "s/\$WAM_V8_CODE_CACHE_SWITCHES//g" $script

        # disable pinch zoom
        sed -i '/--enable-aggressive-release-policy \\/a\     --disable-pinch \\' $script

        if ${TOUCH_ENABLED}; then
           # enable touch events
           sed -i 's/--touch-events=disabled/--touch-events=enabled/' $script
           # enable touch events (with 10 touch points)
           sed -i '/--enable-aggressive-release-policy \\/a\   --force-max-touch-points=10 \\' $script
        else
           # ignore touch devices
           sed -i '/--enable-aggressive-release-policy \\/a\   --ignore-touch-devices \\' $script
        fi

        sed -i '/export WAM_COMMON_SWITCHES=\" \\/a\    --enable-neva-media-service \\' $script

        # disable web media player neva if DISABLE_NEVA_MEDIA_PLAYER is true
        if ${DISABLE_NEVA_MEDIA_PLAYER}; then
           # enable h/w decoding for webrtc
           sed -i '/--enable-aggressive-release-policy \\/a\    --disable-web-media-player-neva \\' $script
        fi

        sed -i '/export WAM_MEM_FLAGS=\" \\/a\    --local-storage-limit-per-second-level-domain=10 \\' $script

        # Extra added for chromium87
        sed -i '/--ozone-platform/d' $script
        sed -i '/export WAM_COMMON_SWITCHES=\" \\/a\    --disable-gpu-vsync \\' $script
        sed -i '/export WAM_COMMON_SWITCHES=\" \\/a\    --enable-accurate-seek \\' $script
    fi
}

install_units:append:qemux86() {
    script=${D}${systemd_system_unitdir}/scripts/webapp-mgr.sh

    # Remove this condition once webos wam is synchronized to get systemd initscripts
    if [ -f "$script" ]; then
        # Disable MCIL hardware acceleration
        sed -i '/--enable-aggressive-release-policy \\/a\   --disable-accelerated-video-decode \\' $script
    fi
}

install_units:append:qemux86-64() {
    script=${D}${systemd_system_unitdir}/scripts/webapp-mgr.sh

    # Remove this condition once webos wam is synchronized to get systemd initscripts
    if [ -f "$script" ]; then
        # Disable MCIL hardware acceleration
        sed -i '/--enable-aggressive-release-policy \\/a\   --disable-accelerated-video-decode \\' $script
    fi
}

PACKAGES =+ "${PN}-tests"
FILES:${PN}-tests = "${webos_testsdir}/* ${libexecdir}/tests/*"

FILES:${PN} += " \
    ${sysconfdir}/pmlog.d \
    ${sysconfdir}/wam \
    ${libdir}/webappmanager/plugins/*.so \
    ${datadir}/localization/${BPN} \
"
