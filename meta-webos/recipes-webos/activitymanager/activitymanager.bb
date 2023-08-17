# Copyright (c) 2012-2023 LG Electronics, Inc.

DESCRIPTION = "webOS component to manage all running activities."
AUTHOR = "Guruprasad KN <guruprasad.kn@lge.com>"
SECTION = "webos/dameons"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "luna-service2 db8 boost libpbnjson glib-2.0 pmloglib ${VIRTUAL-RUNTIME_init_manager}"

WEBOS_VERSION = "3.0.0-40_8da546299f22de459467d24e2d52f1d611ce2daa"
PR = "r13"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_daemon
inherit webos_machine_impl_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "activitymanager.service"

FILES:${PN} += "${webos_sysbus_datadir}"
FILES:${PN} += "${@oe.utils.conditional('DISTRO_NAME', 'webOS OSE', '${localstatedir}/lib/activitymanager', '', d)}"

EXTRA_OECMAKE += "-DINIT_MANAGER:STRING=${VIRTUAL-RUNTIME_init_manager}"

# All service files will be managed in meta-lg-webos.
# The service file in the repository is not used, so please delete it.
# See the page below for more details.
# http://collab.lge.com/main/pages/viewpage.action?pageId=2031668745
do_install:append() {
    if ${@oe.utils.conditional('DISTRO_NAME', 'webOS OSE', 'true', 'false', d)} ; then
        install -m 0700 -o system -g system -v -d ${D}${localstatedir}/lib/activitymanager
    fi

    rm ${D}${sysconfdir}/systemd/system/activitymanager.service
}
