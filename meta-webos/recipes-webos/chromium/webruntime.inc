# Copyright (c) 2018-2024 LG Electronics, Inc.

SUMMARY = "Chromium webruntime for webOS"
AUTHOR = "Donghyun Kim <donghyun11.kim@lge.com>"
SECTION = "webos/apps"

require webruntime-common.inc
require exportbuildscript.inc

inherit gettext
inherit pkgconfig
inherit python3native
inherit webruntime_apps
inherit webos_configure_manifest
inherit webos_filesystem_paths
inherit webos_lttng
inherit webos_machine_dep
inherit webos_machine_impl_dep
inherit webos_prerelease_dep
inherit webos_system_bus
inherit features_check
ANY_OF_DISTRO_FEATURES = "vulkan opengl"

DEPENDS = "virtual/gettext wayland wayland-native luna-service2 pixman freetype fontconfig openssl pango cairo icu webos-wayland-extensions libxkbcommon libexif dbus pciutils udev libcap alsa-lib virtual/egl elfutils-native libdrm atk gperf-native gconf libwebosi18n bison-native xorgproto nss nspr curl nspr-native nss-native pmloglib glib-2.0-native python3-six-native python3-beautifulsoup4-native python3-soupsieve-native python3-lxml-native python3-html5lib-native python3-webencodings-native"

INC_PR = "r73"
# Enable this in webos-local.conf or in recipe to build
DEPLOY_CHROMEDRIVER ?= "false"
DEPLOY_WAM_DEMO ?= "false"
DEPLOY_BROWSER[vardeps] += "VIRTUAL-RUNTIME_com.webos.app.browser"
DEPLOY_BROWSER ?= "${@oe.utils.conditional('VIRTUAL-RUNTIME_com.webos.app.browser', 'com.webos.app.browser', 'true', 'false', d)}"

WEBRUNTIME_BUILD_TARGET = "webos:weboswebruntime"
BROWSER_APP_BUILD_TARGET = "${@oe.utils.conditional('DEPLOY_BROWSER', 'true', 'chrome', '', d)}"
APPSHELL_BUILD_TARGET = "app_shell"
CHROMEDRIVER_BUILD_TARGET = "${@oe.utils.conditional('DEPLOY_CHROMEDRIVER', 'true', 'chromedriver', '', d)}"
WAM_DEMO_CONFARGS = "${@oe.utils.conditional('DEPLOY_WAM_DEMO', 'true', 'is_wam_demo_cbe=true', '', d)}"
WAM_DEMO_BUILD_TARGET = "${@oe.utils.conditional('DEPLOY_WAM_DEMO', 'true', 'wam_demo', '', d)}"

TARGET = "${WEBRUNTIME_BUILD_TARGET} ${BROWSER_APP_BUILD_TARGET} ${APPSHELL_BUILD_TARGET} ${CHROMEDRIVER_BUILD_TARGET} ${WAM_DEMO_BUILD_TARGET}"

# Skip do_install:append of webos_system_bus. It is not compatible with this component.
WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"

PACKAGECONFIG_WEBOS_CODEC ?= "webos-codec pulseaudio webos-camera webos-audio"
PACKAGECONFIG_MEDIA ?= "neva-media gstreamer umediaserver gav"
PACKAGECONFIG ?= ""
PACKAGECONFIG:append = " ${@bb.utils.filter('DISTRO_FEATURES', 'smack', d)}"
PACKAGECONFIG:append:hardware = " ${PACKAGECONFIG_MEDIA} ${PACKAGECONFIG_WEBOS_CODEC}"
PACKAGECONFIG:append:qemux86 = " ${PACKAGECONFIG_MEDIA}"
PACKAGECONFIG:append:qemux86-64 = " ${PACKAGECONFIG_MEDIA}"
PACKAGECONFIG[gstreamer] = "use_gst_media=true enable_webm_video_codecs=false,use_gst_media=false,g-media-pipeline"
PACKAGECONFIG[umediaserver] = ",,umediaserver"
# Options to enable debug build. Add this PACKAGECONFIG to webos-local.conf to enable debug build
# By default debug is completely disabled to speed up build
PACKAGECONFIG[debug] = "symbol_level=2,symbol_level=1"
PACKAGECONFIG[debug-blink] = "blink_symbol_level=2,blink_symbol_level=1"
PACKAGECONFIG[neva-media] = "use_neva_media=true, use_neva_media=false"
PACKAGECONFIG[av1-codec] = "enable_av1_decoder=true, enable_av1_decoder=false"
PACKAGECONFIG[gav] = "use_gav=true,use_gav=false"
PACKAGECONFIG[webos-codec] = "use_webos_codec=true,use_webos_codec=false,media-codec-interface"
PACKAGECONFIG[pulseaudio] = "use_pulseaudio=true, use_pulseaudio=false, pulseaudio"

PACKAGECONFIG:append = " ${@bb.utils.contains('WEBOS_LTTNG_ENABLED', '1', 'lttng', '', d)}"
PACKAGECONFIG[lttng] = "use_lttng=true,use_lttng=false,lttng-ust,lttng-tools lttng-modules babeltrace"
PACKAGECONFIG[webos-camera] = "use_webos_camera=true,use_webos_camera=false, cambufferlib"
PACKAGECONFIG[webos-audio] = "use_webos_audio=true,use_webos_audio=false, pulseaudio"
PACKAGECONFIG[test-coverage] = "enable_neva_test_coverage_build=true,enable_neva_test_coverage_build=false"
PACKAGECONFIG[smack] = "enable_webos_smack=true, enable_webos_smack=false"
PACKAGECONFIG[dcheck] = "dcheck_is_configurable=true"

GN_ARGS:append = " \
    enable_memorymanager_webapi=true\
    ffmpeg_branding=\"Chrome\"\
    is_app_shell_cbe=true\
    ozone_auto_platforms=false\
    proprietary_codecs=true\
    use_bundled_fontconfig=false\
    use_cbe=true\
    use_cups=false\
    use_kerberos=false\
    use_local_storage_tracker=true\
    use_ozone=true\
    use_pmlog=true\
    ${WAM_DEMO_CONFARGS}\
    ${PACKAGECONFIG_CONFARGS}\
"

# More options to speed up the build
GN_ARGS += "\
    enable_nacl=false\
    disable_ftp_support=true\
    enable_print_preview=false\
    enable_remoting=false\
    use_gnome_keyring=false\
"

# TODO: drop this after we migrate to ubuntu 16.04 or above
GN_ARGS += "\
    fatal_linker_warnings=false\
"

# Following options help build with icecc
GN_ARGS += "\
    use_debug_fission=false\
"

# Set webos system bus
GN_ARGS:append = "\
    can_use_legacy_luna_role_files=false\
"

#CHROMIUM_PLUGINS_PATH = "${libdir}"
CBE_DATA_PATH = "${libdir}/cbe"
CBE_DATA_LOCALES_PATH = "${CBE_DATA_PATH}/neva_locales"

do_compile:append() {
    ${NINJA} webos/install/weboswebruntime

    if ${DEPLOY_BROWSER} ; then
        ${NINJA} webos/install/default_browser
    fi

    ${NINJA} webos/install/app_shell

    if ${DEPLOY_CHROMEDRIVER} ; then
        ${NINJA} webos/install/chromedriver
    fi

    if ${DEPLOY_WAM_DEMO} ; then
        ${NINJA} webos/install/wam_demo
    fi
}

install_ls2_roles() {
    # sysbus service files
    install -d ${D}${webos_sysbus_pubservicesdir}
    install -d ${D}${webos_sysbus_prvservicesdir}
    install -v -m 0644 ${OUT_IMAGE_DIR}${webos_sysbus_pubservicesdir}/$1.service ${D}${webos_sysbus_pubservicesdir}/$1.service
    install -v -m 0644 ${OUT_IMAGE_DIR}${webos_sysbus_prvservicesdir}/$1.service ${D}${webos_sysbus_prvservicesdir}/$1.service

    # sysbus role files
    install -d ${D}${webos_sysbus_pubrolesdir}
    install -d ${D}${webos_sysbus_prvrolesdir}
    install -v -m 0644 ${OUT_IMAGE_DIR}${webos_sysbus_pubrolesdir}/$1.json ${D}${webos_sysbus_pubrolesdir}/$1.json
    install -v -m 0644 ${OUT_IMAGE_DIR}${webos_sysbus_prvrolesdir}/$1.json ${D}${webos_sysbus_prvrolesdir}/$1.json

    # sysbus container file
    if [ -f ${OUT_IMAGE_DIR}${webos_sysbus_containersdir}/$1.container.json ]; then
        install -d ${D}${webos_sysbus_containersdir}
        install -v -m 0644 ${OUT_IMAGE_DIR}${webos_sysbus_containersdir}/$1.container.json ${D}${webos_sysbus_containersdir}/$1.container.json
    fi
}

install_ls2_roles_acg() {
    # sysbus service file
    install -d ${D}${webos_sysbus_servicedir}
    install -v -m 0644 ${OUT_IMAGE_DIR}${webos_sysbus_servicedir}/$1.service ${D}${webos_sysbus_servicedir}/$1.service

    # sysbus perm file
    install -d ${D}${webos_sysbus_permissionsdir}
    install -v -m 0644 ${OUT_IMAGE_DIR}${webos_sysbus_permissionsdir}/$1.perm.json ${D}${webos_sysbus_permissionsdir}/$1.perm.json

    # sysbus role file
    install -d ${D}${webos_sysbus_rolesdir}
    install -v -m 0644 ${OUT_IMAGE_DIR}${webos_sysbus_rolesdir}/$1.role.json ${D}${webos_sysbus_rolesdir}/$1.role.json

    # sysbus container file
    if [ -f ${OUT_IMAGE_DIR}${webos_sysbus_containersdir}/$1.container.json ]; then
        install -d ${D}${webos_sysbus_containersdir}
        install -v -m 0644 ${OUT_IMAGE_DIR}${webos_sysbus_containersdir}/$1.container.json ${D}${webos_sysbus_containersdir}/$1.container.json
    fi
}

modify_run_app_shell() {
    # Used by various .bbappends to call "sed" to "configure" various features in ${APP_SHELL_RUNTIME_RUN_SCRIPT}
    # Define APP_SHELL_RUNTIME_RUN_SCRIPT for convenience, so that .bbappends don't need to know it's name and location. just because existing .bbappends expects it to be set
    # The function is called from do_install after it always gets "fresh" run_app_shell from OUT_IMAGE_DIR
    # so you don't need to worry much about "sed" calls being re-entrant when do_install task gets re-executed.
    true
}

modify_run_app_shell:append:qemux86() {
    # Disable MCIL hardware acceleration
    sed -i '/--disable-web-security\\/a\ --disable-accelerated-video-decode\\' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
}
modify_run_app_shell:append:qemux86-64() {
    # Disable MCIL hardware acceleration
    sed -i '/--disable-web-security\\/a\ --disable-accelerated-video-decode\\' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
}

WINDOW_SIZE ?= "1920,1080"
CACHE_DIR ?= "${webos_homedir}/webbrowser"

do_install() {
    install -d ${D}${libdir}
    # libcbe.so, libminigbm.so, liblttng_provider.so
    install -v -m 0644 ${OUT_IMAGE_DIR}/${libdir}/*.so ${D}${libdir}

    install_ls2_roles_acg ${APP_SHELL_RUNTIME}

    install -d ${D}${CBE_DATA_PATH}
    install -v -m 0644 ${OUT_IMAGE_DIR}/${CBE_DATA_PATH}/icudtl.dat ${D}${CBE_DATA_PATH}
    install -v -m 0644 ${OUT_IMAGE_DIR}/${CBE_DATA_PATH}/snapshot_blob.bin ${D}${CBE_DATA_PATH}
    # app_runtime_content.pak, webos_content.pak, extensions_shell_and_test.pak
    install -v -m 0644 ${OUT_IMAGE_DIR}/${CBE_DATA_PATH}/*.pak ${D}${CBE_DATA_PATH}

    install -d ${D}${CBE_DATA_LOCALES_PATH}
    cp -R --no-dereference --preserve=mode,links -v ${OUT_IMAGE_DIR}/${CBE_DATA_LOCALES_PATH}/*.pak ${D}${CBE_DATA_LOCALES_PATH}
    chown root:root -R ${D}${CBE_DATA_LOCALES_PATH}

    install -d ${D}${includedir}/webruntime
    cp -R --no-dereference --preserve=mode,links -v ${OUT_IMAGE_DIR}/${includedir}/webruntime/* ${D}${includedir}/webruntime/
    chown root:root -R ${D}${includedir}/webruntime

    install -d ${D}${webos_pkgconfigdir}
    install -v -m 0644 ${OUT_IMAGE_DIR}${webos_pkgconfigdir}/libcbe.pc ${D}/${webos_pkgconfigdir}

    install -d ${D}${APP_SHELL_RUNTIME_DIR}
    install -v -m 0755 ${OUT_IMAGE_DIR}/${APP_SHELL_RUNTIME_DIR}/app_shell ${D}${APP_SHELL_RUNTIME_DIR}
    install -v -m 0755 ${OUT_IMAGE_DIR}/${APP_SHELL_RUNTIME_DIR}/run_app_shell ${D}${APP_SHELL_RUNTIME_DIR}
    modify_run_app_shell

    ln -f ${D}${CBE_DATA_PATH}/snapshot_blob.bin ${D}${APP_SHELL_RUNTIME_DIR}/snapshot_blob.bin

    if ${DEPLOY_BROWSER} ; then
        install_ls2_roles_acg ${BROWSER_APPLICATION}

        install -d ${D}${BROWSER_APPLICATION_DIR}
        install -v -m 0755 ${OUT_IMAGE_DIR}/${BROWSER_APPLICATION_DIR}/chrome ${D}${BROWSER_APPLICATION_DIR}
        install -v -m 0755 ${OUT_IMAGE_DIR}/${BROWSER_APPLICATION_DIR}/kill_webbrowser ${D}${BROWSER_APPLICATION_DIR}
        install -v -m 0755 ${OUT_IMAGE_DIR}/${BROWSER_APPLICATION_DIR}/run_webbrowser ${D}${BROWSER_APPLICATION_DIR}

        USER_AGENT="Mozilla/5.0 (Linux; NetCast; U) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/${CHROMIUM_VERSION} Safari/537.31"
        echo "${USER_AGENT}" > ${D}${BROWSER_APPLICATION_DIR}/user_agent_conf
        # TODO: Check if we need next 2 settings
        #echo "${CACHE_DIR}" > ${D}${BROWSER_APPLICATION_DIR}/user_cachedir_conf
        # We can replace below WINDOW_SIZE values from build configuration if available
        #echo "${WINDOW_SIZE}" > ${D}${BROWSER_APPLICATION_DIR}/window_size_conf
    fi

    if ${DEPLOY_CHROMEDRIVER} ; then
        install -d ${D}${CHROMEDRIVER_RUNTIME_DIR}
        install -v -m 0755 ${OUT_IMAGE_DIR}/${CHROMEDRIVER_RUNTIME_DIR}/${CHROMEDRIVER_RUNTIME} ${D}${CHROMEDRIVER_RUNTIME_DIR}
    fi

    if ${DEPLOY_WAM_DEMO} ; then
        install_ls2_roles_acg ${WAM_DEMO_APPLICATION}

        install -d ${D}${WAM_DEMO_APPLICATION_DIR}
        install -v -m 0755 ${OUT_IMAGE_DIR}/${WAM_DEMO_APPLICATION_DIR}/wam_demo ${D}${WAM_DEMO_APPLICATION_DIR}
        install -v -m 0755 ${OUT_IMAGE_DIR}/${WAM_DEMO_APPLICATION_DIR}/run_wam_demo ${D}${WAM_DEMO_APPLICATION_DIR}
    fi
}

SYSROOT_DIRS:append = " ${base_bindir}"

PACKAGES:prepend = " \
    ${BROWSER_APPLICATION} \
    ${APP_SHELL_RUNTIME} \
    ${CHROMEDRIVER_RUNTIME} \
    ${WAM_DEMO_APPLICATION} \
"

FILES:${BROWSER_APPLICATION} += " \
    ${BROWSER_APPLICATION_DIR} \
"

FILES:${APP_SHELL_RUNTIME} += " \
    ${APP_SHELL_RUNTIME_DIR} \
"

FILES:${CHROMEDRIVER_RUNTIME} += " \
    ${CHROMEDRIVER_RUNTIME_DIR} \
"

FILES:${WAM_DEMO_APPLICATION} += " \
    ${WAM_DEMO_APPLICATION_DIR} \
"

RDEPENDS:${BROWSER_APPLICATION} += "${PN}"
RDEPENDS:${APP_SHELL_RUNTIME} += "${PN} ${VIRTUAL-RUNTIME_gpu-libs}"

VIRTUAL-RUNTIME_gpu-libs ?= ""
RDEPENDS:${PN} += "${VIRTUAL-RUNTIME_gpu-libs} ${APP_SHELL_RUNTIME}"
RDEPENDS:${WAM_DEMO_APPLICATION} += "${VIRTUAL-RUNTIME_gpu-libs}"

# it aborts if libudev.so.1 is missing
RDEPENDS:${PN} += "libudev"

# for libpci.so
RDEPENDS:${PN} += "libpci"

# The text relocations are intentional -- see comments in [GF-52468]
# TODO: check if we need INSANE_SKIP on ldflags
INSANE_SKIP:${PN} = "textrel ldflags"
INSANE_SKIP:${PN}-dbg += "libdir"
INSANE_SKIP:${MLPREFIX}${BROWSER_APPLICATION} += "libdir ldflags textrel"
INSANE_SKIP:${MLPREFIX}${APP_SHELL_RUNTIME} += "libdir ldflags textrel"
INSANE_SKIP:${MLPREFIX}${CHROMEDRIVER_RUNTIME} += "libdir ldflags textrel"
INSANE_SKIP:${MLPREFIX}${WAM_DEMO_APPLICATION} += "libdir ldflags textrel"

FILES:${PN} += " \
    ${libdir}/*.so \
    ${CBE_DATA_PATH}/* \
    ${libdir}/${BPN}/*.so \
"

FILES:${PN}-dev = " \
    ${webos_pkgconfigdir}/libcbe.pc \
    ${includedir} \
"

export CCACHE_MAXSIZE = "3800M"

