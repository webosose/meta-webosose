# Copyright (c) 2016-2024 LG Electronics, Inc.

# Maintained by Seungho Park <seunghoh.park@lge.com>
DESCRIPTION = "A tool to convert jsdoc to typescript definition files"
AUTHOR = "EnactUnassigned <enact.swp@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.md;beginline=117;md5=fc6d432d87734ce8bb4b52b375ed6766"

inherit npm
inherit webos_enact_repo
inherit native

PR = "r0"

S = "${WORKDIR}/git"

SRC_URI = " \
    ${ENACTJS_GIT_REPO}/jsdoc-to-ts.git;name=jsdoc-to-ts${WEBOS_GIT_PROTOCOL};nobranch=1 \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
"

# latest is 1.0.4
PV = "1.0.0"
SRCREV = "c2d04d1b4a14405a13ee27080fc1545b8c4649cd"
