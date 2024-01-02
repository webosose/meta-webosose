# Copyright (c) 2012-2024 LG Electronics, Inc.

SUMMARY="The Download Manager service supports the downloading and uploading of files to and from a HP webOS device."
AUTHOR = "Guruprasad KN <guruprasad.kn@lge.com>"
SECTION = "webos/apps"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2714381d01eb5a6e963e62a212e277be \
"

DEPENDS = "libpbnjson luna-service2 sqlite3 curl uriparser pmloglib jemalloc luna-prefs boost glib-2.0"
RDEPENDS:${PN} = "applicationinstallerutility"

WEBOS_VERSION = "4.0.0-12_abc1c307bbd50aa2a362c256098da1199220c277"
PR = "r12"

inherit webos_component
inherit webos_library
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_machine_dep
inherit webos_public_repo

WEBOS_MACHINE ?= "${MACHINE}"
EXTRA_OECMAKE += "-DMACHINE=${WEBOS_MACHINE}"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "luna-download-mgr.service.in"
WEBOS_SYSTEMD_SCRIPT = "luna-download-mgr.sh"

# All service files will be managed in meta-lg-webos.
# The service file in the repository is not used, so please delete it.
# See the page below for more details.
# http://collab.lge.com/main/pages/viewpage.action?pageId=2031668745
do_install:append() {
    rm -f ${D}${sysconfdir}/systemd/system/luna-download-mgr.service
    rm -f ${D}${sysconfdir}/systemd/system/scripts/luna-download-mgr.sh
}
