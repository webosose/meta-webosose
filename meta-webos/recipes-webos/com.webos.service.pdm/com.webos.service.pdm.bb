# Copyright (c) 2019-2022 LG Electronics, Inc.

SUMMARY = "Physical Device Manager handles physical devices using netlink events"
DESCRIPTION = "Service for detecting and managing physical devices using netlink events. A physical device is a USB device, available internal storage device and so on."
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/services"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

VIRTUAL-RUNTIME_pdm-plugin ?= "pdm-plugin"

DEPENDS = "glib-2.0 luna-service2 libpbnjson pmloglib udev libwebosi18n libusb"
RDEPENDS:${PN} = "fuse-utils hdparm gphoto2 gphotofs sdparm gptfdisk-sgdisk e2fsprogs-e2fsck e2fsprogs-tune2fs ntfs-3g ntfs-3g-ntfsprogs dosfstools simple-mtpfs lsof smartmontools"

RDEPENDS:${PN} += "${VIRTUAL-RUNTIME_pdm-plugin}"

WEBOS_VERSION = "1.0.1-70_bbcea98b19cd40107c6a7173516ef9438380fec9"
PR = "r6"

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
inherit useradd

USERADD_PARAM:${PN} = "-u 1023 -d /home/pdmuser -m -s /bin/sh pdmuser"
GROUPADD_PARAM:${PN} = "-g 2023 pdmgroup"
USERADD_PACKAGES = "${PN}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES:${PN} += "${datadir}"

# webos doesn't have localization data for this recipe
WEBOS_LOCALIZATION_INSTALL_RESOURCES = "false"
