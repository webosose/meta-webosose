# Copyright (c) 2021-2022 LG Electronics, Inc.
# This file configures common SRC_URI for both of mksnapshot and webruntime
# Also it contains variables for gn command such as GN_ARGS

LICENSE = "Apache-2.0 & BSD-3-Clause & LGPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = "\
    file://src/LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d \
    file://src/third_party/blink/renderer/core/LICENSE-LGPL-2;md5=36357ffde2b64ae177b2494445b79d21 \
    file://src/third_party/blink/renderer/core/LICENSE-LGPL-2.1;md5=a778a33ef338abbaf8b8a7c36b6eec80 \
"

require gn-utils.inc

inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_prerelease_dep

# make sure that python(2) is available, otherwise gn fails to execute python after it was removed from HOSTTOOLS in:
# http://git.openembedded.org/openembedded-core/commit/?id=5f8f16b17f66966ae91aeabc23e97de5ecd17447
#
# /jenkins/mjansa/build-ros-v3/artifacts/BUILD-webos-melodic-master.mcf/work/qemux86-webos-linux/webruntime/68.0.3440.106-75-r24.1/git/depot_tools/gn: line 8: exec: python: not found
# using python3 isn't supported in this old gn, it fails e.g. in gclient_utils.py because of invalid syntax for python3:
# Traceback (most recent call last):
#  File "/jenkins/mjansa/build-ros-v3/artifacts/BUILD-webos-melodic-master.mcf/work/qemux86-webos-linux/webruntime/68.0.3440.106-75-r24.1/git/depot_tools/gn.py", line 15, in <module>
#    import gclient_utils
#  File "/jenkins/mjansa/build-ros-v3/artifacts/BUILD-webos-melodic-master.mcf/work/qemux86-webos-linux/webruntime/68.0.3440.106-75-r24.1/git/depot_tools/gclient_utils.py", line 271
#    except OSError, e:
#                  ^
# SyntaxError: invalid syntax
# most (maybe all) of these issues were already fixed upstream in:
# https://chromium-review.googlesource.com/c/chromium/tools/depot_tools/+/1854900
# but there is a lot of conflicts in old gn used by this webruntime and there are many other places also still using python(2) here
# so lets use native python2 for now
inherit pythonnative

REPO_VERSION := "${@['','_${WEBRUNTIME_REPO_VERSION}'][d.getVar('WEBRUNTIME_REPO_VERSION').strip() != '']}"

WEBOS_REPO_NAME ?= "chromium84"
WEBOS_GIT_PARAM_BRANCH_V8 ?= "@chromium84"
WEBOS_GIT_BRANCH_V8 ?= ";branch=${WEBOS_GIT_PARAM_BRANCH_V8}"
WEBOS_REPO_NAME_V8 ?= "chromium-v8"
WEBOSOSE_GIT_REPO_COMPLETE_V8 ?= "${WEBOSOSE_GIT_REPO}/${WEBOS_REPO_NAME_V8}${WEBOS_GIT_BRANCH_V8}${WEBOSOSE_GIT_PROTOCOL}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE};name=main"

SRC_URI_append = "\
    ${WEBOSOSE_GIT_REPO_COMPLETE_V8};destsuffix=git/src/v8;name=v8 \
"

## we don't include SRCPV in PV, so we have to manually include SRCREVs in do_fetch vardeps
do_fetch[vardeps] += "SRCREV_v8"
SRCREV_FORMAT = "main_v8"

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
    is_clang=false\
    is_cross_linux_build=true\
    is_webos=true\
    is_debug=false\
    is_component_build=false\
    target_os=\"linux\"\
    cros_host_sysroot=\"/\"\
    treat_warnings_as_errors=false\
    use_custom_libcxx=false\
    use_custom_libcxx_for_host=false\
    use_sysroot=false\
"

# Respect ld-is-gold in DISTRO_FEATURES when enabling gold
# Similar patch applied in meta-browser
# http://patchwork.openembedded.org/patch/77755/
EXTRA_OEGN_GOLD = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'use_gold=true', 'use_gold=false', d)}"
GN_ARGS += "${EXTRA_OEGN_GOLD}"

# The last digits in the submission format are used for typical submissions number.
# The submission formats; submissions/[number] or submissions/[branch name].[number]
WEBOS_SUBMISSION_NUMBER="${@ '${WEBOS_SUBMISSION}'.split('.')[-1] }"
GN_ARGS_append = " webos_submission_number=${WEBOS_SUBMISSION_NUMBER}"

TARGET_CPU = "${@gn_arch_name(TARGET_ARCH)}"
GN_ARGS_append = " target_cpu=\"${TARGET_CPU}\""

# ARM builds need special additional flags (see ${S}/build/config/arm.gni).
ARM_FLOAT_ABI = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'hard', 'softfp', d)}"
GN_ARGS_append_armv6 = " arm_arch=\"armv6\" arm_version=6 arm_float_abi=\"${ARM_FLOAT_ABI}\""
GN_ARGS_append_armv7a = " arm_arch=\"armv7-a\" arm_version=7 arm_float_abi=\"${ARM_FLOAT_ABI}\""
GN_ARGS_append_armv7ve = " arm_arch=\"armv7ve\" arm_version=7 arm_float_abi=\"${ARM_FLOAT_ABI}\""
# tcmalloc's atomicops-internals-arm-v6plus.h uses the "dmb" instruction that
# is not available on (some?) ARMv6 models, which causes the build to fail.
GN_ARGS_append_armv6 = ' use_allocator="none"'
# The WebRTC code fails to build on ARMv6 when NEON is enabled.
# https://bugs.chromium.org/p/webrtc/issues/detail?id=6574
GN_ARGS_append_armv6 = ' arm_use_neon=false'

# Doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"

do_configure() {
    export GYP_CHROMIUM_NO_ACTION=1
    export PATH="${S}/depot_tools:$PATH"

    GN_ARGS="${GN_ARGS}"
    echo GN_ARGS is "${GN_ARGS}"
    echo BUILD_TARGETS are ${TARGET}
    cd ${S}/src
    gn gen ${OUT_DIR} --args="${GN_ARGS}"
}

do_compile[progress] = "outof:^\[(\d+)/(\d+)\]\s+"
do_compile() {
    if [ ! -f ${OUT_DIR}/build.ninja ]; then
        do_configure
    fi

    export PATH="${S}/depot_tools:$PATH"
    ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} ${TARGET}
}
