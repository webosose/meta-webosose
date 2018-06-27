# Copyright (c) 2018 LG Electronics, Inc.

inherit ${@'webos_public_repo webos_enhanced_submissions' if '${MACHINE}'.startswith('raspberrypi3') else ''}

GST_COMMON_GIT_rpi = "git://anongit.freedesktop.org/gstreamer/common"
SRCREV_common_rpi = "46d420818ed8172022caf4503894076b18b1e153"
SRCREV_FORMAT_rpi = "main_common"

S_rpi = "${WORKDIR}/git"

SRC_URI_rpi = " \
    ${WEBOSOSE_GIT_REPO_COMPLETE};name=main \
    ${GST_COMMON_GIT};name=common;protocol=git;destsuffix=git/common \
"
do_configure_rpi () {
    ${S}/autogen.sh --noconfigure
    oe_runconf
}
