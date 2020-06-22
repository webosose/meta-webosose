# Copyright (c) 2013-2020 LG Electronics, Inc.

SUMMARY = "JavaScript nub that gets loaded into web application for use with Tellurium framework"
AUTHOR = "Chanwoo Yoo <chanwoo.yoo@lge.com>"
SECTION = "webos/tools"
LICENSE = "CLOSED"

WEBOS_VERSION = "2.2.6-12_bd5521839ffc632a3b1996f48b2bbf297330c8d1"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake

SRC_URI = "${WEBOS_PRO_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

FILES_${PN} += "${webos_prefix}"
