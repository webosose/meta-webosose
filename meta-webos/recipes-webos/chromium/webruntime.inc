# Copyright (c) 2018-2021 LG Electronics, Inc.

SUMMARY = "Chromium webruntime for webOS"
AUTHOR = "Lokesh Kumar Goel <lokeshkumar.goel@lge.com>"
SECTION = "webos/apps"

require webruntime-common.inc

inherit gettext
inherit webruntime_apps
inherit webos_filesystem_paths
inherit webos_lttng
inherit webos_machine_dep
inherit webos_machine_impl_dep
inherit webos_prerelease_dep
inherit webos_system_bus

DEPENDS = "virtual/gettext wayland wayland-native luna-service2 pixman freetype fontconfig openssl pango cairo icu webos-wayland-extensions libxkbcommon libexif dbus pciutils udev libcap alsa-lib virtual/egl elfutils-native libdrm atk gperf-native gconf libwebosi18n bison-native xorgproto nss nspr curl nspr-native nss-native pmloglib glib-2.0-native python-six-native python-beautifulsoup4-native python-lxml-native python-html5lib-native python-webencodings-native"

# Build for raspberrypi4
# Mesa 19.1.1 uses xorgproto instead of xproto series.
DEPENDS_remove_raspberrypi4 = " xproto"
DEPENDS_append_raspberrypi4 = " xorgproto"

INC_PR = "r48"
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

# Skip do_install_append of webos_system_bus. It is not compatible with this component.
WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"
WEBOS_SYSTEM_BUS_FILES_LOCATION = "${S}/files/sysbus"
WEBOS_SYSTEM_BUS_MANIFEST_TYPE = "PASS"

PACKAGECONFIG ?= "jumbo neva-media"
PACKAGECONFIG_append_hardware = " gstreamer umediaserver gav"
PACKAGECONFIG[gstreamer] = "use_gst_media=true enable_webm_video_codecs=false,use_gst_media=false,g-media-pipeline"
PACKAGECONFIG[umediaserver] = ",,umediaserver"
# Options to enable debug build. Add this PACKAGECONFIG to webos-local.conf to enable debug build
# By default debug is completely disabled to speed up build
PACKAGECONFIG[debug] = "symbol_level=2,symbol_level=1"
PACKAGECONFIG[debug-blink] = "blink_symbol_level=2,blink_symbol_level=1"
PACKAGECONFIG[neva-media] = "use_neva_media=true, use_neva_media=false"
PACKAGECONFIG[av1-codec] = "enable_av1_decoder=true, enable_av1_decoder=false"
PACKAGECONFIG[gav] = "use_gav=true,use_gav=false"
PACKAGECONFIG[neva-webrtc] = "use_neva_webrtc=true,use_neva_webrtc=false,media-codec-interface,media-codec-interface"

# Set a default value for jumbo file merge of 8. This should be good for build
# servers and workstations with a big number of cores. In case build is
# happening in a machine with less cores but still enough RAM a good value could
# be 50.
JUMBO_FILE_MERGE_LIMIT="8"
PACKAGECONFIG[jumbo] = "use_jumbo_build=true jumbo_file_merge_limit=${JUMBO_FILE_MERGE_LIMIT}, use_jumbo_build=false"

PACKAGECONFIG_append = " ${@bb.utils.contains('WEBOS_LTTNG_ENABLED', '1', 'lttng', '', d)}"
PACKAGECONFIG[lttng] = "use_lttng=true,use_lttng=false,lttng-ust,lttng-tools lttng-modules babeltrace"

GN_ARGS_append = " \
    enable_memorymanager_webapi=true\
    ffmpeg_branding=\"Chrome\"\
    is_app_shell_cbe=true\
    ozone_auto_platforms=false\
    proprietary_codecs=true\
    use_bundled_fontconfig=false\
    use_cbe=true\
    use_cups=false\
    use_kerberos=false\
    use_local_storage_manager=true\
    use_ozone=true\
    use_pmlog=true\
    use_system_debugger_abort=true\
    use_xkbcommon=true\
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
    use_pulseaudio=false\
"

# Disable closure_compile
# Else we need HOSTTOOLS += "java"
GN_ARGS_append = " closure_compile=false"

# TODO: drop this after we migrate to ubuntu 16.04 or above
GN_ARGS += "\
    fatal_linker_warnings=false\
"

# Following options help build with icecc
GN_ARGS += "\
    use_debug_fission=false\
"


#CHROMIUM_PLUGINS_PATH = "${libdir}"
CBE_DATA_PATH = "${libdir}/cbe"
CBE_DATA_LOCALES_PATH = "${CBE_DATA_PATH}/neva_locales"


do_compile_append() {
    ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} webos/install/weboswebruntime

    if ${DEPLOY_BROWSER} ; then
        ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} webos/install/default_browser
    fi

    ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} webos/install/app_shell

    if ${DEPLOY_CHROMEDRIVER} ; then
        ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} webos/install/chromedriver
    fi

    if ${DEPLOY_WAM_DEMO} ; then
        ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} webos/install/wam_demo
    fi
}

install_ls2_roles() {
    #sysbus files *.service
    install -d ${D}${webos_sysbus_pubservicesdir}
    install -d ${D}${webos_sysbus_prvservicesdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/$1.service ${D}${webos_sysbus_pubservicesdir}/$1.service
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/$1.service ${D}${webos_sysbus_prvservicesdir}/$1.service

    #sysbus files *.json
    install -d ${D}${webos_sysbus_pubrolesdir}
    install -d ${D}${webos_sysbus_prvrolesdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/$1.json ${D}${webos_sysbus_pubrolesdir}/$1.json
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/$1.json ${D}${webos_sysbus_prvrolesdir}/$1.json

    #sysbus files *.container.json
    if [ -f ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/$1.container.json ]; then
        install -d ${D}${webos_sysbus_containersdir}
        install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/$1.container.json ${D}${webos_sysbus_containersdir}/$1.container.json
    fi
}

install_ls2_roles_acg() {
    #sysbus files *.service
    install -d ${D}${webos_sysbus_servicedir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/$1.service ${D}${webos_sysbus_servicedir}/$1.service

    #sysbus files *.json
    install -d ${D}${webos_sysbus_permissionsdir}
    install -d ${D}${webos_sysbus_rolesdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/$1.perm.json ${D}${webos_sysbus_permissionsdir}/$1.perm.json
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/$1.role.json ${D}${webos_sysbus_rolesdir}/$1.role.json
}

install_chromium_manifest() {
    install -d ${D}${webos_sysbus_manifestsdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${BPN}.manifest.json ${D}${webos_sysbus_manifestsdir}/${BPN}.manifest.json
}

modify_run_app_shell() {
    # Used by various .bbappends to call "sed" to "configure" various features in ${APP_SHELL_RUNTIME_RUN_SCRIPT}
    # Define APP_SHELL_RUNTIME_RUN_SCRIPT for convenience, so that .bbappends don't need to know it's name and location. just because existing .bbappends expects it to be set
    # The function is called from do_install after it always gets "fresh" run_app_shell from OUT_IMAGE_DIR
    # so you don't need to worry much about "sed" calls being re-entrant when do_install task gets re-executed.
    true
}

modify_run_app_shell_append_qemux86() {
    # Disable media hardware acceleration
    sed -i '/--disable-web-security\\/a\ --disable-web-media-player-neva\\' ${D}${APP_SHELL_RUNTIME_RUN_SCRIPT}
}

WINDOW_SIZE ?= "1920,1080"
CACHE_DIR ?= "${webos_homedir}/webbrowser"

do_install() {
    install -d ${D}${libdir}
    # libcbe.so, libminigbm.so, liblttng_provider.so
    install -v -m 0644 ${OUT_IMAGE_DIR}/${libdir}/*.so ${D}${libdir}

    install -d ${D}${CBE_DATA_PATH}
    install -v -m 0644 ${OUT_IMAGE_DIR}/${CBE_DATA_PATH}/icudtl.dat ${D}${CBE_DATA_PATH}
    install -v -m 0644 ${OUT_IMAGE_DIR}/${CBE_DATA_PATH}/snapshot_blob.bin ${D}${CBE_DATA_PATH}
    # app_runtime_content.pak, webos_content.pak, extensions_shell_and_test.pak
    install -v -m 0644 ${OUT_IMAGE_DIR}/${CBE_DATA_PATH}/*.pak ${D}${CBE_DATA_PATH}

    install -d ${D}${CBE_DATA_LOCALES_PATH}
    cp -R --no-dereference --preserve=mode,links -v ${OUT_IMAGE_DIR}/${CBE_DATA_LOCALES_PATH}/*.pak ${D}${CBE_DATA_LOCALES_PATH}
    chown root:root -R ${D}${CBE_DATA_LOCALES_PATH}

    install -d ${D}${includedir}/${BPN}
    cp -R --no-dereference --preserve=mode,links -v ${OUT_IMAGE_DIR}/${includedir}/${BPN}/* ${D}${includedir}/${BPN}/
    chown root:root -R ${D}${includedir}/${BPN}

    install -d ${D}${APP_SHELL_RUNTIME_DIR}
    install -v -m 0755 ${OUT_IMAGE_DIR}/${APP_SHELL_RUNTIME_DIR}/app_shell ${D}${APP_SHELL_RUNTIME_DIR}
    install -v -m 0755 ${OUT_IMAGE_DIR}/${APP_SHELL_RUNTIME_DIR}/run_app_shell ${D}${APP_SHELL_RUNTIME_DIR}
    modify_run_app_shell

    ln -f ${D}${CBE_DATA_PATH}/snapshot_blob.bin ${D}${APP_SHELL_RUNTIME_DIR}/snapshot_blob.bin

    if ${DEPLOY_BROWSER} ; then
        install_ls2_roles ${BROWSER_APPLICATION}

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
        install_ls2_roles ${WAM_DEMO_APPLICATION}

        install -d ${D}${WAM_DEMO_APPLICATION_DIR}
        install -v -m 0755 ${OUT_IMAGE_DIR}/${WAM_DEMO_APPLICATION_DIR}/wam_demo ${D}${WAM_DEMO_APPLICATION_DIR}
        install -v -m 0755 ${OUT_IMAGE_DIR}/${WAM_DEMO_APPLICATION_DIR}/run_wam_demo ${D}${WAM_DEMO_APPLICATION_DIR}
    fi

    install_chromium_manifest
}

do_install_append_webos() {
    install_ls2_roles_acg ${APP_SHELL_RUNTIME}
}

WEBOS_SYSTEM_BUS_DIRS_LEGACY_BROWSER_APPLICATION = " \
    ${webos_sysbus_prvservicesdir}/${BROWSER_APPLICATION}.service \
    ${webos_sysbus_pubservicesdir}/${BROWSER_APPLICATION}.service \
    ${webos_sysbus_prvrolesdir}/${BROWSER_APPLICATION}.json \
    ${webos_sysbus_pubrolesdir}/${BROWSER_APPLICATION}.json \
"

WEBOS_SYSTEM_BUS_DIRS_LEGACY_APP_SHELL_RUNTIME = " \
    ${webos_sysbus_prvservicesdir}/${APP_SHELL_RUNTIME}.service \
    ${webos_sysbus_pubservicesdir}/${APP_SHELL_RUNTIME}.service \
    ${webos_sysbus_prvrolesdir}/${APP_SHELL_RUNTIME}.json \
    ${webos_sysbus_pubrolesdir}/${APP_SHELL_RUNTIME}.json \
    ${webos_sysbus_containersdir}/${APP_SHELL_RUNTIME}.container.json \
"

WEBOS_SYSTEM_BUS_DIRS_ACG_APP_SHELL_RUNTIME = " \
    ${webos_sysbus_servicedir}/${APP_SHELL_RUNTIME}.service \
    ${webos_sysbus_permissionsdir}/${APP_SHELL_RUNTIME}.perm.json \
    ${webos_sysbus_rolesdir}/${APP_SHELL_RUNTIME}.role.json \
"

WEBOS_SYSTEM_BUS_DIRS_LEGACY_WAM_DEMO_APPLICATION = " \
    ${webos_sysbus_prvservicesdir}/${WAM_DEMO_APPLICATION}.service \
    ${webos_sysbus_pubservicesdir}/${WAM_DEMO_APPLICATION}.service \
    ${webos_sysbus_prvrolesdir}/${WAM_DEMO_APPLICATION}.json \
    ${webos_sysbus_pubrolesdir}/${WAM_DEMO_APPLICATION}.json \
    ${webos_sysbus_containersdir}/${WAM_DEMO_APPLICATION}.container.json \
"

SYSROOT_DIRS_append = " ${base_bindir}"

PACKAGES_prepend = " \
    ${BROWSER_APPLICATION} \
    ${APP_SHELL_RUNTIME} \
    ${CHROMEDRIVER_RUNTIME} \
    ${WAM_DEMO_APPLICATION} \
"

FILES_${BROWSER_APPLICATION} += " \
    ${BROWSER_APPLICATION_DIR} \
    ${WEBOS_SYSTEM_BUS_DIRS_LEGACY_BROWSER_APPLICATION} \
"

FILES_${APP_SHELL_RUNTIME} += " \
    ${APP_SHELL_RUNTIME_DIR} \
    ${WEBOS_SYSTEM_BUS_DIRS_ACG_APP_SHELL_RUNTIME} \
    ${WEBOS_SYSTEM_BUS_DIRS_LEGACY_APP_SHELL_RUNTIME} \
"

FILES_${CHROMEDRIVER_RUNTIME} += " \
    ${CHROMEDRIVER_RUNTIME_DIR} \
"

FILES_${WAM_DEMO_APPLICATION} += " \
    ${WAM_DEMO_APPLICATION_DIR} \
    ${WEBOS_SYSTEM_BUS_DIRS_LEGACY_WAM_DEMO_APPLICATION} \
"

RDEPENDS_${BROWSER_APPLICATION} += "${PN}"
RDEPENDS_${APP_SHELL_RUNTIME} += "${PN} ${VIRTUAL-RUNTIME_gpu-libs}"

VIRTUAL-RUNTIME_gpu-libs ?= ""
RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_gpu-libs} ${APP_SHELL_RUNTIME}"
RDEPENDS_${WAM_DEMO_APPLICATION} += "${VIRTUAL-RUNTIME_gpu-libs}"

# The text relocations are intentional -- see comments in [GF-52468]
# TODO: check if we need INSANE_SKIP on ldflags
INSANE_SKIP_${PN} = "textrel ldflags"
INSANE_SKIP_${PN}-dbg += "libdir"
INSANE_SKIP_${MLPREFIX}${BROWSER_APPLICATION} += "libdir ldflags textrel"
INSANE_SKIP_${MLPREFIX}${APP_SHELL_RUNTIME} += "libdir ldflags textrel"
INSANE_SKIP_${MLPREFIX}${CHROMEDRIVER_RUNTIME} += "libdir ldflags textrel"
INSANE_SKIP_${MLPREFIX}${WAM_DEMO_APPLICATION} += "libdir ldflags textrel"

FILES_${PN} = " \
    ${libdir}/*.so \
    ${CBE_DATA_PATH}/* \
    ${libdir}/${BPN}/*.so \
    ${WEBOS_SYSTEM_BUS_DIRS} \
"

FILES_${PN}-dev = " \
    ${includedir} \
"
