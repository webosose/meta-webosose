# Copyright (c) 2019-2020 LG Electronics, Inc.

SUMMARY = "The sampler for swupdater"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/apps"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r1"

inherit webos_enactjs_app

require swupdater.inc

WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE = "false"
WEBOS_ENACTJS_PACK_OPTS = ""

WEBOS_REPO_NAME = "com.webos.service.swupdater"
S = "${WORKDIR}/git/application"
