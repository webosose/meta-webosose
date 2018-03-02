# Copyright (c) 2016-2017 LG Electronics, Inc.

inherit gettext
inherit webos_chromium53
inherit webos_chromium53_browser
inherit webos_enhanced_submissions
inherit webos_filesystem_paths
inherit webos_lttng
inherit webos_machine_dep
inherit webos_machine_impl_dep
inherit webos_prerelease_dep
inherit webos_system_bus

DEPENDS = "virtual/gettext wayland wayland-native luna-service2 pixman freetype fontconfig openssl pango cairo icu webos-wayland-extensions libxkbcommon libexif dbus pciutils udev libcap alsa-lib virtual/egl elfutils-native libdrm atk gperf-native gconf libwebosi18n"

DEPENDS_append_hardware = " libndl-directmedia2"

CHROMIUM_VERSION = "53.0.2785.34"
WEBOS_VERSION = "${CHROMIUM_VERSION}-${CHROMIUM53_SUBMISSION}_${CHROMIUM53_SUBMISSION_HASH}"
INC_PR = "r12"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

SRC_DIR = "${S}/src"
OUT_DIR = "${WORKDIR}/build"
BUILD_TYPE = "Release"

WEBOS_SYSTEM_BUS_FILES_LOCATION = "${OUT_DIR}/${BUILD_TYPE}/services"

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

DEBUG_FLAGS = ""

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

do_install() {
    install_chromium_browser
}
