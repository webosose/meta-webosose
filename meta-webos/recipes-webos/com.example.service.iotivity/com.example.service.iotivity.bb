SUMMARY = "IoTivity sample Node JS service"
DESCRIPTION = "Provides server and client services written in JavaScript. Server simulates the binary switch resource defined in OCF specification. Client is able to discover and control the server resource."
HOMEPAGE = "https://www.iotivity.org/"
DEPENDS = "boost virtual/gettext chrpath-replacement-native expat openssl util-linux curl glib-2.0 glib-2.0-native"
DEPENDS += "sqlite3 luna-service2 libpbnjson iotivity"
RDEPENDS:${PN} = "iotivity-resource"

SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = " \
    git://github.com/iotivity/iotivity.git;branch=webos;protocol=https;name=main \
    file://0001-webOS-Apply-new-ACG-policy-in-test-applications.patch \
    file://0001-webOS-Apply-proper-ACG-group-name-in-test-appl.patch \
    file://0001-webOS-Fix-bug-that-API-is-not-called.patch \
"

SRCREV = "ff1837a569494cb9613c3b6c961fcf26f0014515"
S = "${WORKDIR}/git"
PR = "r4"
PV = "1.3.99+git${SRCPV}"

inherit pkgconfig webos_component webos_filesystem_paths

do_install:append() {
    # Application / dat files
    install -d -g 5000 -m 0775 ${D}${webos_servicesdir}/com.example.service.iotivity.server
    install -d -g 5000 -m 0775 ${D}${webos_servicesdir}/com.example.service.iotivity.client
    install -v -g 5000 -m 0775 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/*.* ${D}${webos_servicesdir}/com.example.service.iotivity.server/
    install -v -g 5000 -m 0775 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/*.* ${D}${webos_servicesdir}/com.example.service.iotivity.client/

    # ACG configuration files
    install -d ${D}${datadir}/luna-service2/roles.d
    install -d ${D}${datadir}/luna-service2/services.d
    install -d ${D}${datadir}/luna-service2/client-permissions.d
    install -d ${D}${datadir}/luna-service2/api-permissions.d
    install -d ${D}${datadir}/luna-service2/manifests.d
    install -d ${D}${datadir}/luna-service2/groups.d

    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.service ${D}${datadir}/luna-service2/services.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.role.json ${D}${datadir}/luna-service2/roles.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.perm.json ${D}${datadir}/luna-service2/client-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.api.json ${D}${datadir}/luna-service2/api-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.manifest.json ${D}${datadir}/luna-service2/manifests.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.groups.json ${D}${datadir}/luna-service2/groups.d

    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.service ${D}${datadir}/luna-service2/services.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.role.json ${D}${datadir}/luna-service2/roles.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.perm.json ${D}${datadir}/luna-service2/client-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.api.json ${D}${datadir}/luna-service2/api-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.manifest.json ${D}${datadir}/luna-service2/manifests.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.groups.json ${D}${datadir}/luna-service2/groups.d
}

FILES:${PN} = "\
    ${webos_servicesdir} \
    ${datadir}/luna-service2 \
"

# iotivity doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv6 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"
COMPATIBLE_MACHINE:x86 = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"
