# Copyright (c) 2012-2024 LG Electronics, Inc.

DESCRIPTION = "webOS OSE image"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r5${IMAGE_PR_SUFFIX}"

IMAGE_FEATURES += "${WEBOS_IMAGE_DEFAULT_FEATURES}"

IMAGE_FEATURES += "${@'' if '${WEBOS_DISTRO_PRERELEASE}' == '' else 'debug-tweaks'}"

inherit webos_image
inherit webos_prerelease_dep
inherit ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'webos_smack_labeling', '', d)}

PR:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

IMAGE_ROOTFS_EXTRA_SPACE = "524288"

SRC_URI = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'file://smack_labels_default', '', d)}"

SMACK_LABELS_FILE = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', '${UNPACKDIR}/smack_labels_default', '', d)}"

do_rootfs[prefuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'do_fetch do_unpack', '', d)}"

ROOTFS_POSTPROCESS_COMMAND += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'smack', ' do_smack_labeling ; ', '', d)} \
"
