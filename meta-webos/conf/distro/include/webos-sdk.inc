# Copyright (c) 2012-2025 LG Electronics, Inc.

# bitbake.conf weak assigns this to be "oecore" before this file is included.
SDK_NAME_PREFIX = "${DISTRO}-sdk"

# bitbake.conf assigns this to be "-oesdk" before this file is included.
SDK_VENDOR = "-${DISTRO}sdk"

WEBOS_SDK_RELEASE = "2024.11"

WEBOS_SDK_BASELINE_BUILD_CODENAME = "master"
WEBOS_SDK_BASELINE_BUILD_ID = "2632"
WEBOS_SDK_BASELINE_BUILD_DATE = "2024-11-18"

WEBOS_SDK_BASELINE_YOCTO = "5.0 (Scarthgap)"

# Versions of cross toolchain components:
WEBOS_SDK_BASELINE_GCC = "13.3.0"
WEBOS_SDK_BASELINE_BINUTILS = "2.42"
WEBOS_SDK_BASELINE_TCLIBC = "2.39"
WEBOS_SDK_BASELINE_KERNEL_HEADERS = "6.6"
WEBOS_SDK_BASELINE_GDB = "14.2"

# This version of the SDK will be used to create binary components whose
# TC_MONIKER will be "tc19(hf)".
# Don't include ${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'hf', '', d)} in SDK_VERSION
# because with multilib enabled the nativesdk patckages are built with aarch64 TUNE_FEATURES and don't
# have "hf" suffix in SDK paths while target packages with lib32- prefix have "hf" suffix and then
# meta-environment and gdb-cross-canadian-arm fail to build, see:
# https://jira2.lgsvl.com/browse/PLAT-68839?focusedCommentId=1106686&page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#comment-1106686
SDK_TCMONIKER = "tc19"

SDK_VERSION = "${SDK_TCMONIKER}-${WEBOS_SDK_RELEASE}-${WEBOS_SDK_BASELINE_BUILD_CODENAME}-${WEBOS_SDK_BASELINE_BUILD_ID}-${WEBOS_SDK_BASELINE_BUILD_DATE}-${WEBOS_DISTRO_BUILD_CODENAME}-${WEBOS_DISTRO_BUILD_ID}"
