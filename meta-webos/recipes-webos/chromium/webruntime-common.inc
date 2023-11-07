# Copyright (c) 2021-2023 LG Electronics, Inc.
# This file configures common SRC_URI for both of mksnapshot and webruntime
# Also it contains variables for gn command such as GN_ARGS

LICENSE = "Apache-2.0 & BSD-3-Clause & LGPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = "\
    ${CHROMIUM_LICENSE_CHKSUM} \
    file://src/third_party/blink/renderer/core/LICENSE-LGPL-2;md5=36357ffde2b64ae177b2494445b79d21 \
    file://src/third_party/blink/renderer/core/LICENSE-LGPL-2.1;md5=a778a33ef338abbaf8b8a7c36b6eec80 \
    ${CHROMIUM_OSS_PKG_INFO_CHKSUM} \
"
CHROMIUM_LICENSE_CHKSUM ?= ""
CHROMIUM_OSS_PKG_INFO_CHKSUM ?= ""

require gn-utils.inc

inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_prerelease_dep

WEBRUNTIME_REPO_VERSION ?= "108"
REPO_VERSION := "${@['','_${WEBRUNTIME_REPO_VERSION}'][d.getVar('WEBRUNTIME_REPO_VERSION').strip() != '']}"

WEBOS_REPO_NAME ?= "chromium${WEBRUNTIME_REPO_VERSION}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE};name=main"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

BUILD_TYPE = "Release"
OUT_DIR = "${B}/${BUILD_TYPE}"
OUT_IMAGE_DIR = "${OUT_DIR}/image"

GN_ARGS = "\
    cros_host_ar=\"${BUILD_AR}\"\
    cros_host_cc=\"${BUILD_CC}\"\
    cros_host_cxx=\"${BUILD_CXX}\"\
    cros_host_ld=\"${BUILD_CXX}\"\
    cros_host_nm=\"${BUILD_NM}\"\
    cros_host_extra_ldflags=\"${BUILD_LDFLAGS}\"\
    cros_host_is_clang=false\
    cros_host_extra_cflags=\"${BUILD_CFLAGS}\"\
    cros_host_extra_cppflags=\"${BUILD_CPPFLAGS}\"\
    cros_host_extra_cxxflags=\"${BUILD_CXXFLAGS}\"\
    cros_target_ar=\"${AR}\"\
    cros_target_cc=\"${CC}\"\
    cros_target_cxx=\"${CXX}\"\
    v8_snapshot_toolchain=\"//build/toolchain/cros:v8_snapshot_clang_${TARGET_CPU}\"\
    host_pkg_config=\"pkg-config-native\"\
    host_os=\"linux\"\
    host_toolchain=\"//build/toolchain/cros:host\" \
    is_cross_linux_build=true\
    is_webos=true\
    is_debug=false\
    is_component_build=false\
    target_os=\"linux\"\
    cros_host_sysroot=\"/\"\
    treat_warnings_as_errors=false\
    use_sysroot=false\
    webos_toolprefix=\"${HOST_PREFIX}\"\
    ${GN_ARGS_CLANG} \
    ${GN_ARGS_LIBCXX} \
"

GN_ARGS_CLANG = "is_clang=false"
GN_ARGS_LIBCXX = "${@bb.utils.contains('USE_WEBRUNTIME_LIBCXX', '1', 'use_custom_libcxx=true', 'use_custom_libcxx=false', d)}"

# Respect ld-is-gold in DISTRO_FEATURES when enabling gold
# Similar patch applied in meta-browser
# http://patchwork.openembedded.org/patch/77755/
EXTRA_OEGN_GOLD = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'use_gold=true', 'use_gold=false', d)}"
GN_ARGS += "${EXTRA_OEGN_GOLD}"

# The last digits in the submission format are used for typical submissions number.
# The submission formats; submissions/[number] or submissions/[branch name].[number]
WEBOS_SUBMISSION_NUMBER="${@ '${WEBOS_SUBMISSION}'.split('.')[-1] }"
GN_ARGS:append = " webos_submission_number=${WEBOS_SUBMISSION_NUMBER}"

TARGET_CPU = "${@gn_arch_name(TARGET_ARCH)}"
GN_ARGS:append = " target_cpu=\"${TARGET_CPU}\""

# ARM builds need special additional flags (see ${S}/build/config/arm.gni).
ARM_FLOAT_ABI = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'hard', 'softfp', d)}"
GN_ARGS:append:armv6 = " arm_arch=\"armv6\" arm_version=6 arm_float_abi=\"${ARM_FLOAT_ABI}\""
GN_ARGS:append:armv7a = " arm_arch=\"armv7-a\" arm_version=7 arm_float_abi=\"${ARM_FLOAT_ABI}\""
GN_ARGS:append:armv7ve = " arm_arch=\"armv7ve\" arm_version=7 arm_float_abi=\"${ARM_FLOAT_ABI}\""
# tcmalloc's atomicops-internals-arm-v6plus.h uses the "dmb" instruction that
# is not available on (some?) ARMv6 models, which causes the build to fail.
GN_ARGS:append:armv6 = ' use_allocator="none"'

# The WebRTC code fails to build on ARMv6 when NEON is enabled.
# https://bugs.chromium.org/p/webrtc/issues/detail?id=6574
GN_ARGS_NEON ?= ""
GN_ARGS_NEON:armv6 = "arm_use_neon=false"
GN_ARGS += "${GN_ARGS_NEON}"

# Handle crash while building v8
GN_ARGS:append:aarch64 = ' v8_enable_external_code_space=false'
GN_ARGS:append:x86-64 = ' v8_enable_external_code_space=false'

# Doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv6 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"
COMPATIBLE_MACHINE:x86 = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"

EXTRA_OEGN ?= ""

do_configure() {
    export GYP_CHROMIUM_NO_ACTION=1
    export PATH="${S}/depot_tools:$PATH"

    # Prevent using this prebuilt libstdc++.so.6 which ends with GLIBCXX_3.4.28
    # when linking with host's /usr/lib64/libicuuc.so.71 which might be linked
    # against newer version (e.g. GLIBCXX_3.4.30 from gcc12) when linking
    # gen-regexp-special-case, bytecode_builtins_list_generator
    rm -f ${S}/src/third_party/llvm-build/Release+Asserts/lib/libstdc++.so.6

    GN_ARGS="${GN_ARGS}"
    echo GN_ARGS is "${GN_ARGS}"
    echo BUILD_TARGETS are ${TARGET}
    cd ${S}/src
    gn --script-executable=${PYTHON} ${EXTRA_OEGN} gen ${OUT_DIR} --args="${GN_ARGS}"
}

# Unfortunately not using ninja-native
NINJA_OPTS = "${PARALLEL_MAKE} -C ${OUT_DIR}"
# Use -v to see the commands executed by ninja
NINJA_VERBOSE ?= ""
NINJA = "${S}/depot_tools/ninja ${NINJA_VERBOSE} ${NINJA_OPTS}"

do_compile[progress] = "outof:^\[(\d+)/(\d+)\]\s+"
do_compile() {
    if [ ! -f ${OUT_DIR}/build.ninja ]; then
        do_configure
    fi

    export PATH="${S}/depot_tools:$PATH"
    # depot_tools ninja requires current directory is chromium/src
    cd ${S}/src
    ${NINJA} ${TARGET}
}
