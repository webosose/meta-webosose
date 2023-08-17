# Copyright (c) 2015-2023 LG Electronics, Inc.

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

WEBOS_VERSION = "1.0.2-79_838283243042c1f1df2a1a0a598eda7341e0d038"
PR = "r61"

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

WAM_DATA_DIR = "${webos_execstatedir}/${BPN}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"

S = "${WORKDIR}/git"

WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"

SYSTEMD_INSTALL_PATH = "${sysconfdir}/systemd/system"

# Enable LTTng tracing capability when enabled in webos_lttng class
EXTRA_OECMAKE += "${@oe.utils.conditional('WEBOS_LTTNG_ENABLED', '1', '-DWEBOS_LTTNG_ENABLED:BOOLEAN=True', '', d)}"

EXTRA_OECMAKE += "-DWAM_DATA_DIR=\"\"${webos_cryptofsdir}/.webappmanager/\"\""
EXTRA_OECMAKE += "-DPLATFORM=${@'PLATFORM_' + '${DISTRO}'.upper().replace('-', '_')}"

EXTRA_OECMAKE += "-DWEBOS_TESTS_DIR=${webos_testsdir}"
EXTRA_OECMAKE += "-DWAM_BUILD_DEFAULT_PLUGIN=${WAM_BUILD_DEFAULT_PLUGIN}"

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

# Flag to control runtime flag for platform decoder
PLATFORM_DECODER_ENABLED ?= "true"

do_configure:append() {
    if [ -f "${S}/files/launch/systemd/webapp-mgr.sh.in" ]; then
      cp ${S}/files/launch/systemd/webapp-mgr.sh.in ${B}/webapp-mgr.sh
    fi
    cp ${S}/files/launch/systemd/webapp-mgr.service ${B}/webapp-mgr.service
    sed -i -e "s/NETWORK_STABLE_TIMEOUT/NETWORK_QUIET_TIMEOUT/gI" -e "s/network-stable-timeout/network-quiet-timeout/gI" ${B}/webapp-mgr.sh
    sed -i '/export WAM_COMMON_SWITCHES=\" \\/a\    --disable-in-process-stack-traces \\' ${B}/webapp-mgr.sh
    sed -i '/export ENABLE_BLINK_FEATURES=/ s/$/,LocalResourceCodeCache,CustomEventExtension,WebCodecs,MediaStreamInsertableStreams/' ${B}/webapp-mgr.sh
    sed -i -e "s/\$WAM_V8_CODE_CACHE_SWITCHES//g" ${B}/webapp-mgr.sh

    # disable pinch zoom
    sed -i '/--enable-aggressive-release-policy \\/a\     --disable-pinch \\' ${B}/webapp-mgr.sh

    if ${TOUCH_ENABLED}; then
       # enable touch events
       sed -i 's/--touch-events=disabled/--touch-events=enabled/' ${B}/webapp-mgr.sh
       # enable touch events (with 10 touch points)
       sed -i '/--enable-aggressive-release-policy \\/a\   --force-max-touch-points=10 \\' ${B}/webapp-mgr.sh
    else
       # ignore touch devices
       sed -i '/--enable-aggressive-release-policy \\/a\   --ignore-touch-devices \\' ${B}/webapp-mgr.sh
    fi

    sed -i '/export WAM_COMMON_SWITCHES=\" \\/a\    --enable-neva-media-service \\' ${B}/webapp-mgr.sh

    # disable web media player neva if DISABLE_NEVA_MEDIA_PLAYER is true
    if ${DISABLE_NEVA_MEDIA_PLAYER}; then
       # enable h/w decoding for webrtc
       sed -i '/--enable-aggressive-release-policy \\/a\    --disable-web-media-player-neva \\' ${B}/webapp-mgr.sh
    else
      # enable platform decoding if PLATFORM_DECODER_ENABLED is true
      if ${PLATFORM_DECODER_ENABLED}; then
         # enable h/w decoding for webrtc
         sed -i '/--enable-aggressive-release-policy \\/a\    --enable-webrtc-platform-video-decoder \\' ${B}/webapp-mgr.sh
      fi
    fi

    sed -i '/export WAM_MEM_FLAGS=\" \\/a\    --local-storage-limit-per-second-level-domain=10 \\' ${B}/webapp-mgr.sh

    # Extra added for chromium87
    sed -i '/--ozone-platform/d' ${B}/webapp-mgr.sh
    sed -i '/export WAM_COMMON_SWITCHES=\" \\/a\    --disable-gpu-vsync \\' ${B}/webapp-mgr.sh
    sed -i '/export WAM_COMMON_SWITCHES=\" \\/a\    --enable-accurate-seek \\' ${B}/webapp-mgr.sh
}

do_configure:append:qemux86() {
    # Remove this condition once webos wam is synchronized to get systemd initscripts
    if [ -f "${B}/webapp-mgr.sh" ]; then
        # Disable MCIL hardware acceleration
        sed -i '/--enable-aggressive-release-policy \\/a\   --disable-accelerated-video-decode \\' ${B}/webapp-mgr.sh
    fi
}

do_configure:append:qemux86-64() {
    # Remove this condition once webos wam is synchronized to get systemd initscripts
    if [ -f "${B}/webapp-mgr.sh" ]; then
        # Disable MCIL hardware acceleration
        sed -i '/--enable-aggressive-release-policy \\/a\   --disable-accelerated-video-decode \\' ${B}/webapp-mgr.sh
    fi
}

do_install:append() {
    install -d ${D}${sysconfdir}/pmlog.d
    install -d ${D}${sysconfdir}/wam
    install -d ${D}${WAM_DATA_DIR}
    install -v -m 644 ${S}/files/launch/security_policy.conf ${D}${sysconfdir}/wam/security_policy.conf
    # add loaderror.html and geterror.js to next to resources directory (webos_localization_resources_dir)
    install -d ${D}${datadir}/localization/${BPN}/
    install -d ${D}${SYSTEMD_INSTALL_PATH}/scripts/
    install -v -m 644 ${B}/webapp-mgr.service ${D}${SYSTEMD_INSTALL_PATH}/webapp-mgr.service
    install -v -m 755 ${B}/webapp-mgr.sh ${D}${SYSTEMD_INSTALL_PATH}/scripts/webapp-mgr.sh
    cp -vf ${WAM_ERROR_SCRIPTS_PATH}/* ${D}${datadir}/localization/${BPN}/
}

PACKAGES =+ "${PN}-tests"
FILES:${PN}-tests = "${webos_testsdir}/* ${libexecdir}/tests/*"

FILES:${PN} += " \
    ${sysconfdir}/pmlog.d \
    ${SYSTEMD_INSTALL_PATH} \
    ${sysconfdir}/wam \
    ${libdir}/webappmanager/plugins/*.so \
    ${datadir}/localization/${BPN} \
    ${WEBOS_SYSTEM_BUS_DIRS} \
"
