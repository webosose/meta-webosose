# Copyright (c) 2017-2021 LG Electronics, Inc.

SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.0.0-19_2f910fb7ca5634bf88fc9985095f4840c7f6b487"
INC_PR = "r4"

EXTRA_INHERIT ?= "webos_arch_indep webos_cmake"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_app
inherit webos_public_repo
inherit ${EXTRA_INHERIT}

WEBOS_REPO_NAME = "test-apps"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git/${BPN}"

FILES_${PN} += "${webos_applicationsdir}"
