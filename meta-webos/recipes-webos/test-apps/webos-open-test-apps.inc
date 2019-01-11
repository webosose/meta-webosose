# Copyright (c) 2017-2019 LG Electronics, Inc.

SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.0.0-4_63a09d642592688df3ac9e5cb567a95be62b5c1d"
INC_PR = "r3"

EXTRA_INHERIT ?= "webos_cmake webos_arch_indep"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_app
inherit webos_public_repo
inherit ${EXTRA_INHERIT}

WEBOS_REPO_NAME = "test-apps"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git/${BPN}"

FILES_${PN} += "${webos_applicationsdir}"
