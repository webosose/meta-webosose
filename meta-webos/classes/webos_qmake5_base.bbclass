# Copyright (c) 2020 LG Electronics, Inc.

inherit qmake5_base

# Use webOS as platform.
# Requires 9901-Add-webos-oe-g-and-webos-oe-clang-platforms.patch in qtbase.
XPLATFORM = "webos-oe-g++"
XPLATFORM_toolchain-clang = "webos-oe-clang"
