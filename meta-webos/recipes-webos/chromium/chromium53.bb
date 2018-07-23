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
inherit webos_chromium53
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

DEPENDS_append_hardware = " libndl-directmedia2"

PR = "r13"
CHROMIUM_VERSION = "53.0.2785.34"
WEBOS_VERSION = "${CHROMIUM_VERSION}-${CHROMIUM53_SUBMISSION}_${CHROMIUM53_SUBMISSION_HASH}"

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

GYP_DEFINES += "use_chromium_cbe=1 use_dynamic_injection_loading=0"

# This variable should be removed.
GYP_DEFINES_append_rpi = " platform_apollo=1"

CHROMIUM_PLUGINS_PATH = "${libdir}"
CBE_DATA_PATH = "${libdir}/cbe"
CBE_DATA_LOCALES_PATH = "${CBE_DATA_PATH}/locales"
GYP_DEFINES += "cbe_data=${CBE_DATA_PATH}"

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

SYSROOT_DIRS_append = " ${bindir_cross}"

PROVIDES = "${BROWSER_APPLICATION}"

PACKAGES += " \
    ${PN}-cross-mksnapshot \
    ${BROWSER_APPLICATION} \
"

FILES_${BROWSER_APPLICATION} += " \
    ${datadir} \
    ${BROWSER_APPLICATION_DIR} \
"

RDEPENDS_${BROWSER_APPLICATION} += "${PN}"

VIRTUAL-RUNTIME_gpu-libs ?= ""
RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_gpu-libs}"

INSANE_SKIP_${BROWSER_APPLICATION} += "libdir"

FILES_${PN} = " \
    ${libdir}/*.so \
    ${CBE_DATA_PATH}/* \
    ${libdir}/${BPN}/*.so \
"

FILES_${PN}-dev = " \
    ${includedir} \
"

FILES_${PN}-cross-mksnapshot = "${bindir_cross}/${HOST_SYS}-mksnapshot.gz"
