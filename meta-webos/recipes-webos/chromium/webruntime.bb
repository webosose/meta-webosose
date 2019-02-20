# Copyright (c) 2018-2019 LG Electronics, Inc.

SUMMARY = "Chromium webruntime for webOS"
AUTHOR = "Lokesh Kumar Goel <lokeshkumar.goel@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0 & BSD-3-Clause & LGPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = "\
    file://src/LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d \
    file://src/third_party/blink/renderer/core/LICENSE-LGPL-2;md5=36357ffde2b64ae177b2494445b79d21 \
    file://src/third_party/blink/renderer/core/LICENSE-LGPL-2.1;md5=a778a33ef338abbaf8b8a7c36b6eec80 \
"

require gn-utils.inc

inherit gettext
inherit webruntime_apps
inherit webos_enhanced_submissions
inherit webos_filesystem_paths
inherit webos_lttng
inherit webos_machine_dep
inherit webos_machine_impl_dep
inherit webos_prerelease_dep
inherit webos_system_bus
inherit webos_public_repo

DEPENDS = "virtual/gettext wayland wayland-native luna-service2 pixman freetype fontconfig openssl pango cairo icu webos-wayland-extensions libxkbcommon libexif dbus pciutils udev libcap alsa-lib virtual/egl elfutils-native libdrm atk gperf-native gconf libwebosi18n bison-native xproto nss nspr curl nspr-native nss-native pmloglib"

PROVIDES = "virtual/webruntime"

WEBOS_VERSION = "68.0.3440.106-40_5836d7168c7dc0dcacf5ccad1ef981e779c494c3"
PR = "r14"
WEBOS_REPO_NAME = "chromium68"

SRC_URI = "\
    ${WEBOSOSE_GIT_REPO_COMPLETE};name=main \
    ${WEBOSOSE_GIT_REPO}/v8;destsuffix=git/src/v8;protocol=ssh;name=v8 \
"

## we don't include SRCPV in PV, so we have to manually include SRCREVs in do_fetch vardeps
WEBOS_VERSION_V8 = "6.8.275.26-10_69b79174f1066787a93fbe83368a1b565737b7de"
do_fetch[vardeps] += "SRCREV_v8"
SRCREV_v8 = "1e3af71f1ff3735e8a5b639c48dfca63a7b8a647"
SRCREV_FORMAT = "main_v8"

S = "${WORKDIR}/git"

SRC_DIR = "${S}/src"
OUT_DIR = "${WORKDIR}/build"
BUILD_TYPE = "Release"

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
PACKAGECONFIG_append = " libpci"
PACKAGECONFIG_append_hardware = " gstreamer umediaserver"
# g-media-pipeline is still broken for aarch64 PLAT-45700 PLAT-45699
PACKAGECONFIG_remove_aarch64 = "gstreamer umediaserver neva-media"
PACKAGECONFIG[gstreamer] = "use_gst_media=true enable_webm_video_codecs=false,use_gst_media=false,g-media-pipeline"
PACKAGECONFIG[umediaserver] = ",,umediaserver"
# Options to enable debug build. Add this PACKAGECONFIG to webos-local.conf to enable debug build
# By default debug is completely disabled to speed up build
PACKAGECONFIG[debug] = "is_debug=false is_component_build=false symbol_level=2, is_debug=false symbol_level=0"
PACKAGECONFIG[debug-webcore] = "remove_webcore_debug_symbols=false,remove_webcore_debug_symbols=true"
PACKAGECONFIG[neva-media] = "use_neva_media=true, use_neva_media=false"
PACKAGECONFIG[libpci] = "use_webos_gpu_info_collector=false,use_webos_gpu_info_collector=true"

# Set a default value for jumbo file merge of 8. This should be good for build
# servers and workstations with a big number of cores. In case build is
# happening in a machine with less cores but still enough RAM a good value could
# be 50.
JUMBO_FILE_MERGE_LIMIT="8"
PACKAGECONFIG[jumbo] = "use_jumbo_build=true jumbo_file_merge_limit=${JUMBO_FILE_MERGE_LIMIT}, use_jumbo_build=false"

PACKAGECONFIG_append = " ${@bb.utils.contains('WEBOS_LTTNG_ENABLED', '1', 'lttng', '', d)}"
PACKAGECONFIG[lttng] = "use_lttng=true,use_lttng=false,lttng-ust,lttng-tools lttng-modules babeltrace"

GN_ARGS = "\
    cros_host_ar=\"${BUILD_AR}\"\
    cros_host_cc=\"${BUILD_CC}\"\
    cros_host_cxx=\"${BUILD_CXX}\"\
    cros_host_extra_ldflags=\"${BUILD_LDFLAGS}\"\
    cros_target_ar=\"${AR}\"\
    cros_target_cc=\"${CC}\"\
    cros_target_cxx=\"${CXX}\"\
    enable_memorymanager_webapi=true\
    ffmpeg_branding=\"Chrome\"\
    host_os=\"linux\"\
    is_app_shell_cbe=true\
    is_clang=false\
    is_cross_linux_build=true\
    is_webos=true\
    ozone_auto_platforms=false\
    ozone_platform_wayland_external=true\
    proprietary_codecs=true\
    target_os=\"linux\"\
    target_sysroot=\"${STAGING_DIR_HOST}\"\
    treat_warnings_as_errors=false\
    use_cbe=true\
    use_cups=false\
    use_custom_libcxx=false\
    use_custom_libcxx_for_host=true\
    use_kerberos=false\
    use_ozone=true\
    use_pmlog=true\
    use_sysroot=false\
    use_system_debugger_abort=true\
    use_webos_v8_snapshot=true\
    use_xkbcommon=true\
    ${WAM_DEMO_CONFARGS}\
    ${PACKAGECONFIG_CONFARGS}\
"

# TODO: drop this after we migrate to ubuntu 16.04 or above
GN_ARGS += "\
    is_host_clang=true\
    host_toolchain=\"//build/toolchain/yocto:clang_yocto_native\" \
    fatal_linker_warnings=false\
"

python do_write_toolchain_file () {
    """Writes a BUILD.gn file for Yocto detailing its toolchains."""
    toolchain_dir = d.expand("${S}/src/build/toolchain/yocto")
    bb.utils.mkdirhier(toolchain_dir)
    toolchain_file = os.path.join(toolchain_dir, "BUILD.gn")
    write_toolchain_file(d, toolchain_file)
}
addtask write_toolchain_file after do_patch before do_configure
# end TODO: drop this after we migrate to ubuntu 16.04 or above

# More options to speed up the build
GN_ARGS += "\
    enable_nacl=false\
    disable_ftp_support=true\
    enable_print_preview=false\
    enable_remoting=false\
    use_gnome_keyring=false\
    use_pulseaudio=false\
"

# Following options help build with icecc
GN_ARGS += "\
    linux_use_bundled_binutils=false\
    use_debug_fission=false\
"

# TODO: Check if we need something like below:
# CHROMIUM_DEBUG_FLAGS = "-g1"
# GN_ARGS += “extra_cflags=' ${CHROMIUM_DEBUG_FLAGS} ‘\”
# DEBUG_FLAGS = ""

# Respect ld-is-gold in DISTRO_FEATURES when enabling gold
# Similar patch applied in meta-browser
# http://patchwork.openembedded.org/patch/77755/
EXTRA_OEGN_GOLD = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'use_gold=true', 'use_gold=false', d)}"
GN_ARGS += "${EXTRA_OEGN_GOLD}"

GN_ARGS_append_arm = " target_cpu=\"arm\""
GN_ARGS_append_qemux86 = " target_cpu=\"x86\""
GN_ARGS_append_aarch64 = " target_cpu=\"arm64\""

# TODO: Check if we need anything  like these for GN
# GYP_DEFINES_append_aarch64 = " target_arch=arm64"
# workaround to fix emulator issue with latest chromium
# replace with proper fix when available
# follow: https://bugreports.qt.io/browse/QTBUG-57705
# GYP_DEFINES_append_qemux86 = " generate_character_data=0"

# ARM builds need special additional flags (see ${S}/build/config/arm.gni).
ARM_FLOAT_ABI = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'hard', 'softfp', d)}"
GN_ARGS_append_armv6 = " arm_arch=\"armv6\" arm_version=6 arm_float_abi=\"${ARM_FLOAT_ABI}\""
GN_ARGS_append_armv7a = " arm_arch=\"armv7-a\" arm_version=7 arm_float_abi=\"${ARM_FLOAT_ABI}\""
GN_ARGS_append_armv7ve = " arm_arch=\"armv7ve\" arm_version=7 arm_float_abi=\"${ARM_FLOAT_ABI}\""
# tcmalloc's atomicops-internals-arm-v6plus.h uses the "dmb" instruction that
# is not available on (some?) ARMv6 models, which causes the build to fail.
GN_ARGS_append_armv6 += 'use_allocator="none"'
# The WebRTC code fails to build on ARMv6 when NEON is enabled.
# https://bugs.chromium.org/p/webrtc/issues/detail?id=6574
GN_ARGS_append_armv6 += 'arm_use_neon=false'

# TODO: Add GN corresponding if we support dynamic injection loading
# GYP_DEFINES += "use_dynamic_injection_loading=0"

# Doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"

#CHROMIUM_PLUGINS_PATH = "${libdir}"
CBE_DATA_PATH = "${libdir}/cbe"
CBE_DATA_LOCALES_PATH = "${CBE_DATA_PATH}/locales"

# The text relocations are intentional -- see comments in [GF-52468]
# TODO: check if we need INSANE_SKIP on ldflags
INSANE_SKIP_${PN} = "textrel ldflags"

#ERROR: lib32-webruntime-68.0.3440.106-27-local do_package_qa: QA Issue: /usr/lib/libcbe.so contained in package lib32-webruntime requires libwayland-egl.so, but no providers found in RDEPENDS_lib32-webruntime? [file-rdeps]
# Temporarily until upgrade to newer Yocto 2.6 and proper fix from http://gpro.lgsvl.com/221697 http://gpro.lgsvl.com/221698
INSANE_SKIP_${PN} += "file-rdeps"

do_compile[progress] = "outof:^\[(\d+)/(\d+)\]\s+"
do_compile() {
    if [ ! -f ${OUT_DIR}/${BUILD_TYPE}/build.ninja ]; then
         do_configure
    fi

    export PATH="${S}/depot_tools:$PATH"
    ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR}/${BUILD_TYPE} ${TARGET}
}

do_configure() {
    configure_env
}

configure_env() {
    export GYP_CHROMIUM_NO_ACTION=1
    export PATH="${S}/depot_tools:$PATH"

    GN_ARGS="${GN_ARGS}"
    echo GN_ARGS is ${GN_ARGS}
    echo BUILD_TARGETS are ${TARGET}
    cd ${SRC_DIR}
    gn gen ${OUT_DIR}/${BUILD_TYPE} --args="${GN_ARGS}"
}

WINDOW_SIZE ?= "1920,1080"
CACHE_DIR ?= "${webos_homedir}/webbrowser"

configure_browser_settings() {
    USER_AGENT="Mozilla/5.0 (Linux; NetCast; U) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/${CHROMIUM_VERSION} Safari/537.31"
    echo "${USER_AGENT}" > ${D_DIR}/user_agent_conf
    # TODO: Check if we need next 2 settings
    #echo "${CACHE_DIR}" > ${D_DIR}/user_cachedir_conf
    #We can replace below WINDOW_SIZE values from build configuration if available
    #echo "${WINDOW_SIZE}" > ${D_DIR}/window_size_conf
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
}

install_chromium_browser() {
    D_DIR=${D}${BROWSER_APPLICATION_DIR}
    install -d ${D_DIR}

    # Install browser files
     if [ -e "${SRC_DIR}/webos/install" ]; then
         cd ${OUT_DIR}/${BUILD_TYPE}
         xargs --arg-file=${SRC_DIR}/webos/install/default_browser/binary.list cp -R --no-dereference --preserve=mode,links -v --target-directory=${D_DIR}
         cd ${SRC_DIR}
         xargs --arg-file=${SRC_DIR}/webos/install/default_browser/runtime.list cp -R --no-dereference --preserve=mode,links -v --target-directory=${D_DIR}
     fi

    install_ls2_roles ${BROWSER_APPLICATION}

    # To execute chromium in JAILER, Security Part needs permissions change
    # run_webbrowser: Script file for launching chromium
    chmod -v 755 ${D_DIR}/chrome
    chmod -v 755 ${D_DIR}/kill_webbrowser
    chmod -v 755 ${D_DIR}/run_webbrowser

    configure_browser_settings
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

install_app_shell() {
    A_DIR=${D}${APP_SHELL_RUNTIME_DIR}
    install -d ${A_DIR}

    # Install app-shell files
    if [ -e "${SRC_DIR}/webos/install" ]; then
        cd ${OUT_DIR}/${BUILD_TYPE}
        xargs --arg-file=${SRC_DIR}/webos/install/app_shell/binary.list cp -R --no-dereference --preserve=mode,links -v --target-directory=${A_DIR}
        xargs --arg-file=${SRC_DIR}/webos/install/app_shell/cbe_data.list cp --parents --target-directory=${D}${CBE_DATA_PATH}
        cd ${SRC_DIR}
        xargs --arg-file=${SRC_DIR}/webos/install/app_shell/runtime.list cp -R --no-dereference --preserve=mode,links -v --target-directory=${A_DIR}
    fi

    # To execute chromium in JAILER, Security Part needs permissions change
    # run_appshell: Script file for launching chromium
    chmod -v 755 ${A_DIR}/app_shell
    chmod -v 755 ${A_DIR}/run_app_shell
}

install_app_shell_append_webos() {
    install_ls2_roles_acg ${APP_SHELL_RUNTIME}
}

install_app_shell_append_qemux86() {
    # Disable media hardware acceleration
    sed -i '/--disable-web-security\\/a\ --disable-web-media-player-neva\\' ${D}${APP_SHELL_RUNTIME_DIR}/run_app_shell
}

install_chromium_manifest() {
    install -d ${D}${webos_sysbus_manifestsdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${BPN}.manifest.json ${D}${webos_sysbus_manifestsdir}/${BPN}.manifest.json
}

MKSNAPSHOT_PATH = ""
MKSNAPSHOT_PATH_arm = "clang_x86_v8_arm/"
MKSNAPSHOT_PATH_aarch64 = "clang_x64_v8_arm64/"

install_webruntime() {
    install -d ${D}${libdir}
    install -d ${D}${includedir}/${BPN}
    install -d ${D}${CBE_DATA_PATH}
    install -d ${D}${CBE_DATA_LOCALES_PATH}

    # Install webos webview files
    if [ -e "${SRC_DIR}/webos/install" ]; then
        cd ${SRC_DIR}
        xargs --arg-file=${SRC_DIR}/webos/install/weboswebruntime/staging_inc.list cp --parents --target-directory=${D}${includedir}/${BPN}

        cd ${OUT_DIR}/${BUILD_TYPE}

        cp libcbe.so ${D}${libdir}/
        if [ "${WEBOS_LTTNG_ENABLED}" = "1" ]; then
          # use bindir if building non-cbe
          cp libchromium_lttng_provider.so ${D}${libdir}/
        fi
        xargs --arg-file=${SRC_DIR}/webos/install/weboswebruntime/binary.list cp --parents --target-directory=${D}${CBE_DATA_PATH}
        cat ${SRC_DIR}/webos/install/weboswebruntime/data_locales.list | xargs -I{} install -m 755 -p {} ${D}${CBE_DATA_LOCALES_PATH}
    fi

    # move this to separate mksnapshot-cross recipe once we figure out how to build just cross mksnapshot from chromium repository
    install -d ${D}${bindir_cross}
    gzip -c ${OUT_DIR}/${BUILD_TYPE}/${MKSNAPSHOT_PATH}mksnapshot > ${D}${bindir_cross}/${HOST_SYS}-mksnapshot.gz
}

install_chromedriver() {
    install -d ${D}${CHROMEDRIVER_RUNTIME_DIR}

    # Install browser files
     if [ -e "${SRC_DIR}/webos/install" ]; then
         cd ${OUT_DIR}/${BUILD_TYPE}
         cp -R --no-dereference --preserve=mode,links -v --target-directory=${D}${CHROMEDRIVER_RUNTIME_DIR} ${CHROMEDRIVER_RUNTIME}
     fi

    # To execute chromium in JAILER, Security Part needs permissions change
    # run_webbrowser: Script file for launching chromium
    chmod -v 755 ${D}${CHROMEDRIVER_RUNTIME_DIR}/${CHROMEDRIVER_RUNTIME}
}

install_wam_demo() {
    D_DIR=${D}${WAM_DEMO_APPLICATION_DIR}
    install -d ${D_DIR}

    # Install wam demo files
     if [ -e "${SRC_DIR}/webos/install" ]; then
         cd ${OUT_DIR}/${BUILD_TYPE}
         xargs --arg-file=${SRC_DIR}/webos/install/wam_demo/binary.list cp -R --no-dereference --preserve=mode,links -v --target-directory=${D_DIR}
         xargs --arg-file=${SRC_DIR}/webos/install/wam_demo/cbe_data.list cp --parents --target-directory=${D}${CBE_DATA_PATH}
         cd ${SRC_DIR}
         xargs --arg-file=${SRC_DIR}/webos/install/wam_demo/runtime.list cp -R --no-dereference --preserve=mode,links -v --target-directory=${D_DIR}
     fi

    install_ls2_roles ${WAM_DEMO_APPLICATION}

    # To execute chromium in JAILER, Security Part needs permissions change
    # run_webbrowser: Script file for launching chromium
    chmod -v 755 ${D_DIR}/wam_demo
    chmod -v 755 ${D_DIR}/run_wam_demo
}

do_install() {
    install_webruntime
    if ${DEPLOY_BROWSER} ; then
      install_chromium_browser
    fi
    install_app_shell
    if ${DEPLOY_CHROMEDRIVER} ; then
      install_chromedriver
    fi
    if ${DEPLOY_WAM_DEMO} ; then
      install_wam_demo
    fi
    install_chromium_manifest
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
"

SYSROOT_DIRS_append = " ${bindir_cross}"

PACKAGES_prepend = " \
    ${PN}-cross-mksnapshot \
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

FILES_${PN}-cross-mksnapshot = "${bindir_cross}/${HOST_SYS}-mksnapshot.gz"
