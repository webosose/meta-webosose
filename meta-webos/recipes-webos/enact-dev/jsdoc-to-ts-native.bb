# Copyright (c) 2016-2025 LG Electronics, Inc.

# Maintained by Seungho Park <seunghoh.park@lge.com>
DESCRIPTION = "A tool to convert jsdoc to typescript definition files"
AUTHOR = "EnactUnassigned <enact.swp@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.md;beginline=114;md5=06c21a8c43ba289feb62c713df825cc0"

inherit npm
inherit webos_enact_repo
inherit native

PR = "r0"

S = "${WORKDIR}/git"

SRC_URI = " \
    ${ENACTJS_GIT_REPO}/jsdoc-to-ts.git;name=jsdoc-to-ts${WEBOS_GIT_PROTOCOL};nobranch=1 \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
"

PV = "1.0.6"
SRCREV = "f20070af2612e8bf434ea6aa38782d6f30118ccc"
