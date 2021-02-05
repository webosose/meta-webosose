# Copyright (c) 2013-2021 LG Electronics, Inc.

SUMMARY = "JS service for Developer Mode"
AUTHOR = "Steve Lemke <steve.lemke@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
                    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

WEBOS_VERSION = "1.0.0-10_182786bf4501501763dfd41d27cefe111578851c"
PR = "r7"

WEBOS_SYSTEM_BUS_MANIFEST_TYPE = "PASS"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_machine_impl_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# touch /var/luna/preferences/devmode_enabled in emulator build
do_install_append_emulator() {
    install -d ${D}${webos_sysmgr_localstatedir}/preferences
    touch ${D}${webos_sysmgr_localstatedir}/preferences/devmode_enabled
}

# touch /var/luna/preferences/devmode_enabled in webOS OSE build
do_install_append_webos() {
    install -d ${D}${webos_sysmgr_localstatedir}/preferences
    touch ${D}${webos_sysmgr_localstatedir}/preferences/devmode_enabled
}

do_install_append() {
    mkdir -p ${D}${webos_sysbus_manifestsdir}

    cat > "${D}${webos_sysbus_manifestsdir}/${BPN}.manifest.json" <<END
    {
        "id": "${BPN}",
        "version": "1.0.0",
        "serviceFiles": ["${webos_sysbus_servicedir}/com.palm.service.devmode.service"],
        "roleFiles": ["${webos_sysbus_rolesdir}/com.palm.service.devmode.role.json"],
        "clientPermissionFiles": ["${webos_sysbus_permissionsdir}/com.palm.service.devmode.perm.json"],
        "apiPermissionFiles": ["${webos_sysbus_apipermissionsdir}/com.palm.service.devmode.api.json"]
    }
END

    cat > "${D}${webos_sysbus_manifestsdir}/${BPN}-tests.manifest.json" <<END
    {
        "id": "${BPN}-tests",
        "version": "1.0.0",
        "roleFiles": ["${webos_sysbus_rolesdir}/com.palm.service.devmode.test.json"]
    }
END
}

PACKAGES =+ "${PN}-tests"
FILES_${PN}-tests += "${webos_servicesdir}/${BPN}/tests"
FILES_${PN}-tests += "${webos_sysbus_rolesdir}/com.palm.service.devmode.test.json"
FILES_${PN}-tests += "${webos_sysbus_manifestsdir}/${BPN}-tests.manifest.json"

FILES_${PN}_append = " ${webos_servicesdir}/${BPN}/*"
FILES_${PN}_append_emulator = " ${webos_sysmgr_localstatedir}"
