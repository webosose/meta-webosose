# Copyright (c) 2012-2013 LG Electronics, Inc.
#
# webos_submissions
#
# Parse a WEBOS_VERSION in the following format:
#
#    <component-version>-<submission>
#
# setting WEBOS_COMPONENT_VERSION, WEBOS_SUBMISSION, and PV.
#

inherit webos_version

# When WEBOS_VERSION isn't defined show error
do_fetch[prefuncs] += "webos_submissions_version_sanity_check"

python webos_submissions_version_sanity_check() {
    webos_version = d.getVar('WEBOS_VERSION', True)
    webos_component_version = d.getVar('WEBOS_COMPONENT_VERSION', True)
    pv = d.getVar('PV', True)
    file = d.getVar('FILE', True)
    src_uri = d.getVar('SRC_URI', True)
    if not webos_version or webos_version == '0':
        bb.fatal("%s: WEBOS_VERSION needs to be defined for recipes inheriting webos_submissions or webos_enhanced_submissions" % file)
    if not webos_component_version or webos_component_version == '0':
        bb.fatal("%s: WEBOS_VERSION needs contain WEBOS_COMPONENT_VERSION different from '0'" % file)
    if not pv or pv == '0':
        bb.fatal("%s: WEBOS_VERSION needs contain PV different from '0'" % file)
    if src_uri.find('git://') != -1 and not bb.data.inherits_class('webos_enhanced_submissions', d):
        bb.fatal("%s: inherit webos_enhanced_submissions when the recipe uses git:// in SRC_URI" % file)
}

WEBOS_VERSION ?= "0"
PV = "${@webos_version_get_pv('${WEBOS_VERSION}')}"
WEBOS_COMPONENT_VERSION = "${@webos_version_get_component_version('${WEBOS_VERSION}')}"
WEBOS_SUBMISSION        = "${@webos_version_get_submission('${WEBOS_VERSION}')}"
