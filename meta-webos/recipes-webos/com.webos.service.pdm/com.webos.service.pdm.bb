# Copyright (c) 2019-2020 LG Electronics, Inc.

SUMMARY = "Physical Device Manager handles physical devices using netlink events"
DESCRIPTION = "Service for detecting and managing physical devices using netlink events. A physical device is a USB device, available internal storage device and so on."
SECTION = "webos/services"
AUTHOR = "Preetham Bhat <preetham.bhat@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

VIRTUAL-RUNTIME_pdm-plugin ?= "pdm-plugin"

DEPENDS = "glib-2.0 luna-service2 libpbnjson pmloglib udev libwebosi18n libusb"
RDEPENDS_${PN} = "fuse-utils hdparm gphoto2 gphotofs sdparm gptfdisk-sgdisk e2fsprogs-e2fsck e2fsprogs-tune2fs ntfs-3g ntfs-3g-ntfsprogs dosfstools simple-mtpfs lsof smartmontools"

RDEPENDS_${PN} += "${VIRTUAL-RUNTIME_pdm-plugin}"

WEBOS_VERSION = "1.0.1-29_e2b2e96868c1d1efdc8917c1048cb69f6389ecb8"
PR = "r3"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_daemon
inherit webos_machine_impl_dep
inherit webos_machine_dep
inherit webos_public_repo
inherit webos_localizable
inherit webos_distro_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES_${PN} += "${datadir}"
