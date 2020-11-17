# Copyright (c) 2019-2020 LG Electronics, Inc.

# Enable SOTA common setting
#
# Because of the webOS has various distro for many product,
# some distro has their own FOTA system.
# So, we should possible to remove meta-updater layer without fixing
# this file and that's why I use 'include' directive rather than 'require'.
include conf/distro/sota.conf.inc

# override OSTREE variables
OSTREE_OSNAME = "${DISTRO}"
OSTREE_BRANCHNAME = "${IMAGE_BASENAME}-${WEBOS_DISTRO_BUILD_CODENAME}"
OSTREE_COMMIT_SUBJECT ?= "${IMAGE_BASENAME}${IMAGE_VERSION_SUFFIX}"

# Don't use aktualizr as a SOTA_CLIENT (will create new one)
IMAGE_INSTALL_remove_sota = "aktualizr aktualizr-info"
SOTA_CLIENT_PROV = ""

# Disable sota when target is qemu
INHERIT_remove_qemuall = "sota"
DISTRO_FEATURES_remove_qemuall = "sota usrmerge"
DISTRO_FEATURES_NATIVE_remove_qemuall = "sota"
