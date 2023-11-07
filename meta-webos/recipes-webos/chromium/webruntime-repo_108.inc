# Copyright (c) 2023 LG Electronics, Inc.

WEBOS_REPO_NAME = "chromium108"
WEBRUNTIME_REPO_VERSION = "108"
WEBOS_VERSION = "108.0.5359.217-4_6fdcec6af1af7b9656cb989b2e8ad9894340cd8e"

# LICENSE
CHROMIUM_LICENSE_CHKSUM = "file://src/LICENSE;md5=c408a301e3407c3803499ce9290515d6"
# oss-pkg-info.yaml
CHROMIUM_OSS_PKG_INFO_CHKSUM = "file://oss-pkg-info.yaml;md5=f6cf1c62d76fef64d3ca1b3556fc1d9a"

# http://gpro.lge.com/c/webosose/chromium108/+/373183 Make CheckedContiguousIterator work correctly for libstdc++. (master branch)
# http://gpro.lge.com/c/webosose/chromium108/+/373312 Make CheckedContiguousIterator work correctly for libstdc++. (@pro branch)
# http://gpro.lge.com/c/webosose/chromium94/+/373211 Disable usage of `__is_cpp17_contiguous_iterator` until libc++ rolls.
# http://gpro.lge.com/c/webosose/chromium108/+/373222 Disable usage of `__is_cpp17_contiguous_iterator` until libc++ rolls. (master branch)
# http://gpro.lge.com/c/webosose/chromium108/+/373221 Disable usage of `__is_cpp17_contiguous_iterator` until libc++ rolls. (@pro branch)
# http://gpro.lge.com/c/webosose/chromium108/+/373453 Ensure CheckedContiguousIterator satisfies C++20 Concept (master branch)
# http://gpro.lge.com/c/webosose/chromium108/+/373313 Ensure CheckedContiguousIterator satisfies C++20 Concept (@pro branch)
# http://gpro.lge.com/c/webosose/chromium108/+/373258 traffic_annotation: fix build with clang-17 (master branch)
# http://gpro.lge.com/c/webosose/chromium108/+/373455 traffic_annotation: fix build with clang-17 (@pro branch)
SRC_URI += " \
    file://0001-Make-CheckedContiguousIterator-work-correctly-for-li.patch \
    file://0002-Disable-usage-of-__is_cpp17_contiguous_iterator-unti.patch \
    file://0003-Ensure-CheckedContiguousIterator-satisfies-C-20-Conc.patch \
    file://0004-traffic_annotation-fix-build-with-clang-17.patch \
"

# These 2 are already applied in @pro but missing in master
# http://gpro.lge.com/c/webosose/chromium108/+/373249 [op][upsable][build] Fix build with gcc-13 (master branch)
# http://gpro.lge.com/c/webosose/chromium108/+/351386 [op][upsable][build] Fix build with gcc-13 (@pro branch)
# http://gpro.lge.com/c/webosose/chromium108/+/373250 [op][upsable][build] llvm: Fix build with gcc-13 (master branch)
# http://gpro.lge.com/c/webosose/chromium108/+/352226 [op][upsable][build] llvm: Fix build with gcc-13 (@pro branch)
SRC_URI += " \
    file://0002-Fix-build-with-gcc-13.patch \
    file://0003-llvm-Fix-build-with-gcc-13.patch \
"
