SUMMARY = "IoTivity webapp sample application."
DESCRIPTION = "iotivity-native-sample provides server and client application written in c++. Server simulates the binary switch resource defined in OCF specification. Client is able to discover and control the server resource."
HOMEPAGE = "https://www.iotivity.org/"
DEPENDS = "boost virtual/gettext chrpath-replacement-native expat openssl util-linux curl glib-2.0 glib-2.0-native"
DEPENDS += "sqlite3 luna-service2 libpbnjson iotivity"
RDEPENDS_${PN} = "iotivity-resource"

SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = " \
    git://github.com/iotivity/iotivity.git;branch=webos;protocol=http;name=main \
    git://github.com/intel/tinycbor.git;destsuffix=iotivity/extlibs/tinycbor/tinycbor;protocol=http;name=tinycbor \
    git://github.com/ARMmbed/mbedtls.git;destsuffix=iotivity/extlibs/mbedtls/mbedtls;protocol=http;branch=mbedtls-2.4;name=mbedtls \
"

SRCREV_main = "ff1837a569494cb9613c3b6c961fcf26f0014515"
SRCREV_tinycbor = "ae64a3d9da39f3bf310b9a7b38427c096d8bcd43"
SRCREV_mbedtls = "59ae96f167a19f4d04dc6db61f6587b37ccd429f"

SRCREV_FORMAT = "main"
do_fetch[vardeps] = "SRCREV_main SRCREV_tinycbor SRCREV_mbedtls"

S = "${WORKDIR}/git"

PR = "r1"
PV = "1.3.99+git${SRCPV}"

inherit scons pkgconfig webos_enactjs_app webos_component

WEBOS_ENACTJS_PROJECT_PATH ??= "./resource/csdk/stack/samples/webos/com.example.app.iotivity"
WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE = "false"
WEBOS_ENACTJS_APP_ID = "com.example.app.iotivity"

do_install_append() {
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

    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.service ${D}${datadir}/luna-service2/services.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.role.json ${D}${datadir}/luna-service2/roles.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.perm.json ${D}${datadir}/luna-service2/client-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.api.json ${D}${datadir}/luna-service2/api-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.manifest.json ${D}${datadir}/luna-service2/manifests.d

    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.service ${D}${datadir}/luna-service2/services.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.role.json ${D}${datadir}/luna-service2/roles.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.perm.json ${D}${datadir}/luna-service2/client-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.api.json ${D}${datadir}/luna-service2/api-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.manifest.json ${D}${datadir}/luna-service2/manifests.d
}

FILES_${PN} = "\
    ${webos_applicationsdir} \
    ${webos_servicesdir} \
    ${datadir}/luna-service2 \
"

# iotivity doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"
