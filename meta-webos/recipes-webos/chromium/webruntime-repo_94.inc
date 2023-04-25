# Copyright (c) 2022-2023 LG Electronics, Inc.

WEBOS_VERSION = "94.0.4606.128-22_20f258a27b86bb07e9bf5031cc369db046a6d0c5"

# 9.4.146.25-chromium94.3
SRCREV_v8 = "20f6074f6735d664fc398c1ca74aceb5d291d646"

# [http://gpro.lge.com/c/webosose/chromium-v8/+/344640 Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344612 Add missing include to ShaderVars.h]
# [http://gpro.lge.com/c/webosose/chromium94/+/344613 VulkanMemoryAllocator: Added missing #include <cstdio>]
# [http://gpro.lge.com/c/webosose/chromium94/+/344614 absl/strings/internal/str_format/extension.h: add missing <stdint.h> include]
# [http://gpro.lge.com/c/webosose/chromium94/+/344615 cc/trees: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344616 dawn-tint: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344617 debug: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344618 feature_engagement: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344619 libgav1: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344620 mainline version gcc-13 cannot use "uintptr_t" via "#include <string>"]
# [http://gpro.lge.com/c/webosose/chromium94/+/344621 perfetto: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344622 webrtc: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344623 base/containers: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344624 gpu/config: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344625 password-manager: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344626 device/bluetooth: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344627 components/crash: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344628 device/base: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344629 net/cookies: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344630 net/base: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344631 quiche: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344632 ppapi: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344633 ui: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344634 autofill: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344635 payments: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344636 extensions/browser/api: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344637 third_party/blink: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344638 extensions/renderer: Fix build with gcc-13]
# [http://gpro.lge.com/c/webosose/chromium94/+/344639 swiftshader-llvm: Fix build with gcc-13]
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://0001-Fix-build-with-gcc-13.patch;patchdir=src/v8 \
    file://0001-Add-missing-include-to-ShaderVars.h.patch \
    file://0002-VulkanMemoryAllocator-Added-missing-include-cstdio.patch \
    file://0003-absl-strings-internal-str_format-extension.h-add-mis.patch \
    file://0004-cc-trees-Fix-build-with-gcc-13.patch \
    file://0005-dawn-tint-Fix-build-with-gcc-13.patch \
    file://0006-debug-Fix-build-with-gcc-13.patch \
    file://0007-feature_engagement-Fix-build-with-gcc-13.patch \
    file://0008-libgav1-Fix-build-with-gcc-13.patch \
    file://0009-mainline-version-gcc-13-cannot-use-uintptr_t-via-inc.patch \
    file://0010-perfetto-Fix-build-with-gcc-13.patch \
    file://0011-webrtc-Fix-build-with-gcc-13.patch \
    file://0012-base-containers-Fix-build-with-gcc-13.patch \
    file://0013-gpu-config-Fix-build-with-gcc-13.patch \
    file://0014-password-manager-Fix-build-with-gcc-13.patch \
    file://0015-device-bluetooth-Fix-build-with-gcc-13.patch \
    file://0016-components-crash-Fix-build-with-gcc-13.patch \
    file://0017-device-base-Fix-build-with-gcc-13.patch \
    file://0018-net-cookies-Fix-build-with-gcc-13.patch \
    file://0019-net-base-Fix-build-with-gcc-13.patch \
    file://0020-quiche-Fix-build-with-gcc-13.patch \
    file://0021-ppapi-Fix-build-with-gcc-13.patch \
    file://0022-ui-Fix-build-with-gcc-13.patch \
    file://0023-autofill-Fix-build-with-gcc-13.patch \
    file://0024-payments-Fix-build-with-gcc-13.patch \
    file://0025-extensions-browser-api-Fix-build-with-gcc-13.patch \
    file://0026-third_party-blink-Fix-build-with-gcc-13.patch \
    file://0027-extensions-renderer-Fix-build-with-gcc-13.patch \
    file://0028-swiftshader-llvm-Fix-build-with-gcc-13.patch \
"
