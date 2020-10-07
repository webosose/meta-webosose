# Copyright (c) 2019-2020 LG Electronics, Inc.

SUMMARY = "webOS SDK Toolchain including qt host tools"

PR = "r2"

TOOLCHAIN_HOST_TASK = "nativesdk-packagegroup-sdk-host"
TOOLCHAIN_HOST_TASK += "nativesdk-packagegroup-qt5-toolchain-host"
# Only add packagegroup-cross-canadian-${MACHINE} to TOOLCHAIN_HOST_TASK when
# not using an external toolchain. Once we can figure out how to package up the
# native portion of external-lg-toolchain, the recipe that implements it will
# appear instead. For now, we are manually copying the external toolchain tree
# into the SDK's native sysroot.
EXTERNAL_TOOLCHAIN ??= ""
TOOLCHAIN_HOST_TASK += "${@oe.utils.conditional('EXTERNAL_TOOLCHAIN', '', 'packagegroup-cross-canadian-${MACHINE}', '${MLPREFIX}meta-environment-${MACHINE}', d)}"

# XXX When using external-lg-toolchain, need to explicitly include
# linux-libc-headers because external-lg-toolchain puts the headers in that
# package instead of in linux-libc-headers-dev (which is where the
# linux-libc-headers recipe puts them).
TOOLCHAIN_TARGET_TASK += "${@oe.utils.conditional('EXTERNAL_TOOLCHAIN', '', '', '${MLPREFIX}linux-libc-headers', d)}"

TOOLCHAIN_OUTPUTNAME = "${SDK_NAME}-${WEBOS_DISTRO_BUILD_ID}"

# By default, populate_sdk puts the toolchain in TOOLCHAIN_TARGET_TASK (which
# controls what the bbclass packages).
inherit populate_sdk
inherit populate_sdk_qt5_base

inherit webos_image

inherit webos_machine_impl_dep
inherit webos_machine_dep

IMAGE_FSTYPES = ""
IMAGE_FSTYPES_qemux86 = ""
IMAGE_FSTYPES_qemux86-64 = ""

SDK_NAME = "${BPN}-${DISTRO}-${SDK_ARCH}-${WEBOS_DISTRO_BUILD_CODENAME}-${MACHINE_ARCH}"

# Often triggers:
# Exception: bb.process.ExecutionError: Execution of 'webos-qt-sdk/1.0-r1/temp/run.archive_sdk.41476' failed with exit code 1:
# xz: (stdin): Cannot allocate memory
XZ_MEMLIMIT = "10%"
