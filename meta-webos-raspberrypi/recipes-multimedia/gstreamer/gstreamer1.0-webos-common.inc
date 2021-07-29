# Copyright (c) 2018-2021 LG Electronics, Inc.

# inherit ${@'webos_public_repo webos_enhanced_submissions' if '${MACHINE}'.startswith('raspberrypi3') or '${MACHINE}'.startswith('raspberrypi4') else ''}

# GST_COMMON_GIT:rpi = "git://anongit.freedesktop.org/gstreamer/common;branch=master"
# SRCREV_common:rpi = "46d420818ed8172022caf4503894076b18b1e153"
# SRCREV_FORMAT:rpi = "main_common"

# S:rpi = "${WORKDIR}/git"

# SRC_URI:rpi = " \
#     ${WEBOSOSE_GIT_REPO_COMPLETE};name=main \
#     ${GST_COMMON_GIT};name=common;protocol=git;destsuffix=git/common \
# "
# do_configure:rpi () {
#     ${S}/autogen.sh --noconfigure
#     oe_runconf
# }
