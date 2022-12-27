# Copyright (c) 2022-2023 LG Electronics, Inc.

SUMMARY = "v8 snapshot test application"
AUTHOR = "Wanchang Ryu <wanchang.ryu@lge.com>"

EXTRA_INHERIT = "webos_cmake"

require webos-open-test-apps.inc

PR = "${INC_PR}.1"

DEPENDS = "mksnapshot-cross-${TARGET_ARCH}"

EXTRA_OECMAKE = "-DV8_MKSNAPSHOT=mksnapshot-cross-${TARGET_ARCH}"
