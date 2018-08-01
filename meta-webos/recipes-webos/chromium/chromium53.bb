# Copyright (c) 2016-2018 LG Electronics, Inc.

SUMMARY = "Chromium v53 browser for webOS"
AUTHOR = "Lokesh Kumar Goel <lokeshkumar.goel@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0 & BSD-3-Clause & LGPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = "\
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://src/LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d \
    file://src/third_party/WebKit/Source/core/LICENSE-LGPL-2;md5=36357ffde2b64ae177b2494445b79d21 \
    file://src/third_party/WebKit/Source/core/LICENSE-LGPL-2.1;md5=a778a33ef338abbaf8b8a7c36b6eec80 \
"

inherit gettext
inherit webos_chromium53_browser
inherit webos_enhanced_submissions
inherit webos_filesystem_paths
inherit webos_lttng
inherit webos_machine_dep
inherit webos_machine_impl_dep
inherit webos_prerelease_dep
inherit webos_system_bus
inherit webos_public_repo

DEPENDS = "virtual/gettext wayland wayland-native luna-service2 pixman freetype fontconfig openssl pango cairo icu webos-wayland-extensions libxkbcommon libexif dbus pciutils udev libcap alsa-lib virtual/egl elfutils-native libdrm atk gperf-native gconf libwebosi18n"

PR = "r17"
WEBOS_VERSION = "53.0.2785.34-6_791108609e0ad62db19af8a41c7ac8a11ae61dba"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

SRC_DIR = "${S}/src"
OUT_DIR = "${WORKDIR}/build"
BUILD_TYPE = "Release"

# Skip do_install_append of webos_system_bus. It is not compatible with this component.
WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"
WEBOS_SYSTEM_BUS_FILES_LOCATION = "${S}/files/sysbus"
WEBOS_SYSTEM_BUS_MANIFEST_TYPE = "PASS"

APP_SHELL_RUNTIME = "app-shell"
APP_SHELL_RUNTIME_DIR = "${bindir}/${APP_SHELL_RUNTIME}"

CHROMIUM_DEBUG_FLAGS = "-g1"
DEBUG_FLAGS = ""

GYP_DEFINES = "\
    chromeos=0\
    clang=0\
    component=static_library\
    disable_ftp_support=1\
    disable_nacl=1\
    enable_media_router=0\
    enable_plugin_installation=1\
    enable_plugins=1\
    enable_printing=0\
    enable_webrtc=1\
    ffmpeg_branding=Chrome\
    host_clang=0\
    no_gc_sections=1\
    ozone_auto_platforms=0\
    ozone_platform_caca=0\
    ozone_platform_dri=0\
    ozone_platform_drm=0\
    ozone_platform_eglfs=1\
    ozone_platform_egltest=0\
    ozone_platform_gbm=0\
    ozone_platform_ozonex=0\
    ozone_platform_test=0\
    ozone_platform_wayland_external=1\
    proprietary_codecs=1\
    python_ver=2.7\
    qt_patch=1\
    release_extra_cflags=' ${CHROMIUM_DEBUG_FLAGS} '\
    remoting=0\
    remove_webcore_debug_symbols=0\
    strip_tests=1\
    swig_defines=-DOS_LINUX\
    sysroot=${STAGING_DIR_HOST}\
    system_libdir=lib\
    use_ash=0\
    use_aura=1\
    use_cups=0\
    use_gnome_keyring=0\
    use_ibus=0\
    use_kerberos=0\
    use_nss_certs=0\
    use_nss_verifier=0\
    use_openssl=1\
    use_openssl_certs=1\
    use_ozone=1\
    use_pulseaudio=0\
    use_self_signed_certificates=1\
    use_system_icu=0\
    use_xkbcommon=1\
    webos=1\
    webos_ewam=0\
    werror=''\
"

GYP_DEFINES += "${@bb.utils.contains('WEBOS_LTTNG_ENABLED', '1', ' enable_lttng=1', '', d)}"

# Respect ld-is-gold in DISTRO_FEATURES when enabling gold
# Similar patch applied in meta-browser
# http://patchwork.openembedded.org/patch/77755/
EXTRA_OEGYP_GOLD = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'linux_use_gold_binary=1 linux_use_gold_flags=1', 'linux_use_gold_binary=0 linux_use_gold_flags=0', d)}"

# Use system binutils, bundled binutils (GNU gold (GNU Binutils 2.24) 1.11)
# aren't compatible with (GNU gold (GNU Binutils for Ubuntu 2.25.90.20160101) 1.11) included in Ubuntu-16.04 Alpha
# and building native libvpx fails with:
# chromium38/38.0.2125.122-107-r11.3/git/src/third_party/binutils/Linux_x64/Release/bin/ld: error: /usr/lib/gcc/x86_64-linux-gnu/5/../../../x86_64-linux-gnu/crti.o: unsupported reloc 42 against global symbol __gmon_start__
GYP_DEFINES_append = " linux_use_bundled_gold=0"
GYP_DEFINES_append = " linux_use_bundled_binutils=0"

GYP_DEFINES += "${EXTRA_OEGYP_GOLD}"

GYP_DEFINES_append_arm = " target_arch=arm arm_float_abi=${TUNE_CCARGS_MFLOAT}"
GYP_DEFINES_append_aarch64 = " target_arch=arm64"
GYP_DEFINES_append_qemux86 = " target_arch=ia32"
# workaround to fix emulator issue with latest chromium
# replace with proper fix when available
# follow: https://bugreports.qt.io/browse/QTBUG-57705
GYP_DEFINES_append_qemux86 = " generate_character_data=0"
GYP_DEFINES_append_armv7a = " arm_version=7"
GYP_DEFINES_append_armv7ve = " arm_version=7 arm_override_arch='armv7ve'"

# umediaserver interface for hardware
GYP_DEFINES_append_hardware = " use_umediaserver=1 use_webos_media_focus_extension=1"
DEPENDS_append_hardware = " umediaserver"

# still broken for aarch64 PLAT-45700
GYP_DEFINES_remove_aarch64 = "use_umediaserver=1 use_webos_media_focus_extension=1"
DEPENDS_remove_aarch64 = "umediaserver"

GYP_DEFINES += "use_chromium_cbe=1 use_dynamic_injection_loading=0"

# This variable should be removed or changed. See discussion in PLAT-51087.
GYP_DEFINES_append_webos = " platform_apollo=1"

CHROMIUM_PLUGINS_PATH = "${libdir}"
CBE_DATA_PATH = "${libdir}/cbe"
CBE_DATA_LOCALES_PATH = "${CBE_DATA_PATH}/locales"
GYP_DEFINES += "cbe_data=${CBE_DATA_PATH}"

# Doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"

# The text relocations are intentional -- see comments in [GF-52468]
INSANE_SKIP_${PN} = "textrel"

do_compile[progress] = "outof:^\[(\d+)/(\d+)\]\s+"
do_compile() {
    if [ ! -f ${OUT_DIR}/${BUILD_TYPE}/build.ninja ]; then
         do_configure
    fi

    ${S}/depot_tools/ninja -C ${OUT_DIR}/${BUILD_TYPE} chromium
}

do_configure() {
    configure_env
    echo GYP_DEFINES is $GYP_DEFINES
    echo GYP_GENERATOR_FLAGS is $GYP_GENERATOR_FLAGS
    ${SRC_DIR}/build/gyp_chromium
}

configure_env() {
    CC_host="${BUILD_CC}"
    CXX_host="${BUILD_CXX}"
    LD_host="${BUILD_LD}"
    AR_host="${BUILD_AR}"

    # GYP options
    GYP_DEFINES="${GYP_DEFINES}"
    GYP_GENERATOR_FLAGS="output_dir=${OUT_DIR} config=${BUILD_TYPE}"

    # Lg default build environment
    if [ -e "${SRC_DIR}/lg" ]; then
        export GYP_INCLUDE_FIRST=${SRC_DIR}/lg/build/lg_common.gypi
        export GYP_INCLUDE_LAST=${SRC_DIR}/lg/build/lg_defines.gypi
    fi

    export CC_host CXX_host LD_host AR_host GYP_DEFINES GYP_GENERATOR_FLAGS
}

WINDOW_SIZE ?= "1920,1080"
CACHE_DIR ?= "${webos_homedir}/webbrowser"

configure_browser_settings() {
    echo "${USER_AGENT}" > ${D_DIR}/user_agent_conf

    echo "${CACHE_DIR}" > ${D_DIR}/user_cachedir_conf
    #We can replace below WINDOW_SIZE values from build configuration if available
    echo "${WINDOW_SIZE}" > ${D_DIR}/window_size_conf
}

install_chromium_browser() {
    D_DIR=${D}${BROWSER_APPLICATION_DIR}
    install -d ${D_DIR}
    cp -R --no-dereference --preserve=mode,links -v ${OUT_DIR}/${BUILD_TYPE}/install/* ${D_DIR}/

    #sysbus files *.service
    install -d ${D}${webos_sysbus_pubservicesdir}
    install -d ${D}${webos_sysbus_prvservicesdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${BROWSER_APPLICATION}.service ${D}${webos_sysbus_pubservicesdir}/${BROWSER_APPLICATION}.service
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${BROWSER_APPLICATION}.service ${D}${webos_sysbus_prvservicesdir}/${BROWSER_APPLICATION}.service

    #sysbus files *.json
    install -d ${D}${webos_sysbus_pubrolesdir}
    install -d ${D}${webos_sysbus_prvrolesdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${BROWSER_APPLICATION}.json ${D}${webos_sysbus_pubrolesdir}/${BROWSER_APPLICATION}.json
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${BROWSER_APPLICATION}.json ${D}${webos_sysbus_prvrolesdir}/${BROWSER_APPLICATION}.json

    # To execute chromium in JAILER, Security Part needs permissions change
    # run_webbrowser: Script file for launching chromium
    chmod -v 755 ${D_DIR}/chrome
    chmod -v 755 ${D_DIR}/kill_webbrowser
    chmod -v 755 ${D_DIR}/run_webbrowser

    # disble remote debugging feature for production
    if [ "${WEBOS_DISTRO_PRERELEASE}" = "" ]; then
        sed -i 's/ENABLE_INSPECTOR=1/ENABLE_INSPECTOR=0/' ${D_DIR}/run_webbrowser
    fi

    configure_browser_settings
}

install_app_shell() {
    A_DIR=${D}${APP_SHELL_RUNTIME_DIR}
    install -d ${A_DIR}
    cp -R --no-dereference --preserve=mode,links -v ${OUT_DIR}/${BUILD_TYPE}/install_app_shell/* ${A_DIR}/

    #sysbus files *.service
    install -d ${D}${webos_sysbus_pubservicesdir}
    install -d ${D}${webos_sysbus_prvservicesdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${APP_SHELL_RUNTIME}.service ${D}${webos_sysbus_pubservicesdir}/${APP_SHELL_RUNTIME}.service
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${APP_SHELL_RUNTIME}.service ${D}${webos_sysbus_prvservicesdir}/${APP_SHELL_RUNTIME}.service

    #sysbus files *.json
    install -d ${D}${webos_sysbus_pubrolesdir}
    install -d ${D}${webos_sysbus_prvrolesdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${APP_SHELL_RUNTIME}.json ${D}${webos_sysbus_pubrolesdir}/${APP_SHELL_RUNTIME}.json
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${APP_SHELL_RUNTIME}.json ${D}${webos_sysbus_prvrolesdir}/${APP_SHELL_RUNTIME}.json

    # To execute chromium in JAILER, Security Part needs permissions change
    # run_appshell: Script file for launching chromium
    chmod -v 755 ${A_DIR}/app_shell
    chmod -v 755 ${A_DIR}/run_appshell
}

DEPLOY_BROWSER[vardeps] += "VIRTUAL-RUNTIME_com.webos.app.browser"
DEPLOY_BROWSER ?= "${@oe.utils.conditional('VIRTUAL-RUNTIME_com.webos.app.browser', 'com.webos.app.browser', 'true', 'false', d)}"

install_chromium_manifest() {
    install -d ${D}${webos_sysbus_manifestsdir}
    install -v -m 0644 ${WEBOS_SYSTEM_BUS_FILES_LOCATION}/${BPN}.manifest.json ${D}${webos_sysbus_manifestsdir}/${BPN}.manifest.json
    if ! ${DEPLOY_BROWSER} ; then
        # com.webos.app.browser is not shipped in webosose by chromium
        # drop the role files for com.webos.app.browser from chromium manifest file
        # else we see errors when ls-hubd starts parsing manifest file
        manifest_file="${webos_sysbus_manifestsdir}/chromium53.manifest.json"
        sed -i '/\"\(.*\)com.webos.app.browser/d' ${D}${manifest_file}
        sed -i -e 's:app-shell\.json\",:app-shell\.json\":g' ${D}${manifest_file}
        sed -i -e 's:\"${webos_sysbus_prvservicesdir}\/${APP_SHELL_RUNTIME}\.service\",:\"${webos_sysbus_prvservicesdir}\/${APP_SHELL_RUNTIME}\.service\":g' ${D}${manifest_file}
    fi
}

do_install() {
    install_chromium_browser
    install_app_shell
    install_chromium_manifest
}

do_install_append() {
    install -d ${D}${libdir}
    install -d ${D}${includedir}/${BPN}
    cd ${SRC_DIR}
    xargs --arg-file=${SRC_DIR}/build/cbe_staging_inc.list cp --parents --target-directory=${D}${includedir}/${BPN}
    ln -snf ${BPN} ${D}${includedir}/chromium
    cd ${OUT_DIR}/${BUILD_TYPE}
    xargs --arg-file=${SRC_DIR}/build/cbe_staging_res.list cp --parents --target-directory=${D}${includedir}/${BPN}
    cat ${SRC_DIR}/build/cbe_staging_lib.list | xargs -I{} install -m 755 -p {} ${D}${libdir}
    mkdir -p ${D}${CBE_DATA_PATH}
    cat ${SRC_DIR}/webos/install/cbe/cbe_data.list | xargs -I{} install -m 755 -p {} ${D}${CBE_DATA_PATH}
    if [ "${WEBOS_LTTNG_ENABLED}" = "1" ]; then
    cat ${SRC_DIR}/webos/install/cbe/cbe_data_lttng.list | xargs -I{} install -m 755 -p {} ${D}${CBE_DATA_PATH}
    fi
    mkdir -p ${D}${CBE_DATA_LOCALES_PATH}
    cat ${SRC_DIR}/webos/install/cbe/cbe_data_locales.list | xargs -I{} install -m 755 -p {} ${D}${CBE_DATA_LOCALES_PATH}

    # move this to separate mksnapshot-cross recipe once we figure out how to build just cross mksnapshot from chromium repository
    install -d ${D}${bindir_cross}
    gzip -c ${OUT_DIR}/${BUILD_TYPE}/mksnapshot > ${D}${bindir_cross}/${HOST_SYS}-mksnapshot.gz
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

SYSROOT_DIRS_append = " ${bindir_cross}"

PACKAGES += " \
    ${PN}-cross-mksnapshot \
    ${BROWSER_APPLICATION} \
    ${APP_SHELL_RUNTIME} \
"

FILES_${BROWSER_APPLICATION} += " \
    ${BROWSER_APPLICATION_DIR} \
    ${WEBOS_SYSTEM_BUS_DIRS_LEGACY_BROWSER_APPLICATION} \
"

FILES_${APP_SHELL_RUNTIME} += " \
    ${APP_SHELL_RUNTIME_DIR} \
    ${WEBOS_SYSTEM_BUS_DIRS_LEGACY_APP_SHELL_RUNTIME} \
"

RDEPENDS_${BROWSER_APPLICATION} += "${PN}"

VIRTUAL-RUNTIME_gpu-libs ?= ""
RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_gpu-libs} ${APP_SHELL_RUNTIME}"

INSANE_SKIP_${BROWSER_APPLICATION} += "libdir"

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
