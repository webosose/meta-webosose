# Copyright (c) 2019-2025 LG Electronics, Inc.

SUMMARY = "webOS SDK Toolchain including qt host tools"

PR = "r6"

TOOLCHAIN_HOST_TASK = "nativesdk-packagegroup-sdk-host"
TOOLCHAIN_HOST_TASK += "nativesdk-packagegroup-qt6-toolchain-host"
TOOLCHAIN_HOST_TASK += "packagegroup-cross-canadian-${MACHINE}"

TOOLCHAIN_TARGET_TASK += "${@bb.utils.contains('DISTRO_FEATURES', 'webos-aiframework', 'packagegroup-webos-ml-sdk', '', d)}"

# By default, populate_sdk puts the toolchain in TOOLCHAIN_TARGET_TASK (which
# controls what the bbclass packages).
inherit populate_sdk
inherit populate_sdk_qt6_base

inherit webos_image

inherit webos_machine_impl_dep
inherit webos_machine_dep

IMAGE_FSTYPES = ""
IMAGE_FSTYPES:qemux86 = ""
IMAGE_FSTYPES:qemux86-64 = ""

# Often triggers:
# Exception: bb.process.ExecutionError: Execution of 'webos-qt-sdk/1.0-r1/temp/run.archive_sdk.41476' failed with exit code 1:
# xz: (stdin): Cannot allocate memory
XZ_MEMLIMIT = "10%"
