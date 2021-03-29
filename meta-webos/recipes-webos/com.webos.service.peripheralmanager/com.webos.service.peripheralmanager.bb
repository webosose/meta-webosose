# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "Peripheral Manager service for webOS OSE"
AUTHOR  = "Yogish S <yogish.s@lge.com>"
SECTION = "webosose"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "glib-2.0 libpbnjson luna-service2 pmloglib "

WEBOS_VERSION = "1.0.0-7_d7b327018b60e1700755901627faae19c4708213"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

WEBOS_SYSTEM_BUS_MANIFEST_TYPE = "SERVICE"

FILES_${PN} += "${base_libdir}/*"

SYSTEMD_SERVICE_${PN} += " com.webos.service.peripheralmanager.service"

