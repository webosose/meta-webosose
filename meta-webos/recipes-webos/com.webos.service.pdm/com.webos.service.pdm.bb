# Copyright (c) 2019 LG Electronics, Inc.

SUMMARY = "Physical Device Manager handles physical devices using netlink events"
DESCRIPTION = "Service for detecting and managing physical devices using netlink events. A physical device is a USB device, available internal storage device and so on."
SECTION = "webos/services"
AUTHOR = "Preetham Bhat <preetham.bhat@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

VIRTUAL-RUNTIME_pdm-plugin ?= "pdm-plugin"

DEPENDS = "glib-2.0 luna-service2 libpbnjson pmloglib udev libwebosi18n"
RDEPENDS_${PN} = "fuse-utils gphoto2 gphotofs sdparm gptfdisk-sgdisk e2fsprogs-e2fsck e2fsprogs-tune2fs ntfs-3g ntfs-3g-ntfsprogs dosfstools simple-mtpfs lsof smartmontools"

RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_pdm-plugin}"

WEBOS_VERSION = "1.0.0-8_70b90ff567440f65529b56e6d899ee2dbfd8adf4"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_daemon
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES_${PN} += "${datadir}"
