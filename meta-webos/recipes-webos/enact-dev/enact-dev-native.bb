# Copyright (c) 2016-2025 LG Electronics, Inc.

# Maintained by Seungho Park <seunghoh.park@lge.com>
DESCRIPTION = "enact-dev command-line tools used by webOS"
AUTHOR = "EnactUnassigned <enact.swp@lge.com>"
SECTION = "webos/devel/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9456eea7fa7e9e4a4fcdf8e430bd36c8"

inherit npm
inherit webos_enact_repo
inherit native

# NOTE: It's only necessary to bump PR if the recipe itself changes
# No need to bump PR when changing the values of PV and SRCREV (below)
PR = "r0"

S = "${WORKDIR}/git"

RDEPENDS:${PN} += "jsdoc-to-ts-native"

SRC_URI = " \
    ${ENACTJS_GIT_REPO}/cli.git;name=main${WEBOS_GIT_PROTOCOL};nobranch=1 \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
"

# PV is the version of the cli distribution, as tagged in the
# enactjs/cli repository on GitHub. This version should correspond to the
# tag whose hash is specified in SRCREV, so PV and SRCREV will always change
# together.

PV = "6.1.3"
SRCREV = "3f4d28c3d4a865090943694515c972631ead699c"
