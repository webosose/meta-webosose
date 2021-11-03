SUMMARY = "IoTivity native sample application."
DESCRIPTION = "iotivity-native-sample provides server and client application written in c++. Server simulates the binary switch resource defined in OCF specification. Client is able to discover and control the server resource."
HOMEPAGE = "https://www.iotivity.org/"
DEPENDS = "boost virtual/gettext chrpath-replacement-native expat openssl util-linux curl glib-2.0 glib-2.0-native"
DEPENDS += "sqlite3 luna-service2 libpbnjson iotivity"
RDEPENDS_${PN} = "iotivity-resource"

SECTION = "webos/apps"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI += " \
    git://github.com/iotivity/iotivity.git;branch=webos;protocol=https;name=main;destsuffix=iotivity \
    git://github.com/intel/tinycbor.git;destsuffix=iotivity/extlibs/tinycbor/tinycbor;protocol=https;name=tinycbor \
    git://github.com/ARMmbed/mbedtls.git;destsuffix=iotivity/extlibs/mbedtls/mbedtls;protocol=https;branch=mbedtls-2.4;name=mbedtls \
"

SRCREV_main = "8fe09d4c9859d3ff32b4e8ca2ed996a156fccb81"
SRCREV_tinycbor = "ae64a3d9da39f3bf310b9a7b38427c096d8bcd43"
SRCREV_mbedtls = "59ae96f167a19f4d04dc6db61f6587b37ccd429f"

SRCREV_FORMAT = "main"
do_fetch[vardeps] = "SRCREV_main SRCREV_tinycbor SRCREV_mbedtls"

S = "${WORKDIR}/iotivity"
PR = "r4"

inherit scons pkgconfig webos_component webos_filesystem_paths

IOTIVITY_TARGET_ARCH = "${TARGET_ARCH}"

SRC_URI += " \
    file://0001-Do-independent-build-of-csdk-samples.patch \
    file://0002-webOS-Fix-build-error-detected-by-gcc8.patch \
    file://0001-webOS-Apply-new-ACG-policy-in-test-applications.patch \
    file://0001-webOS-Apply-proper-ACG-group-name-in-test-appl.patch \
"

EXTRA_OESCONS += " \
    -f resource/csdk/stack/samples/webos/secure/build/SConscript \
    TARGET_OS=webos TARGET_TRANSPORT=IP RELEASE=1 \
    TARGET_ARCH=${IOTIVITY_TARGET_ARCH} \
    VERBOSE=1 \
"

do_compile_append() {
    export PKG_CONFIG_PATH="${PKG_CONFIG_PATH}"
    export PKG_CONFIG="PKG_CONFIG_SYSROOT_DIR=\"${PKG_CONFIG_SYSROOT_DIR}\" pkg-config"
    export LD_FLAGS="${LD_FLAGS}"
    scons_do_compile
}

do_install_append() {
    # Application / dat files
    install -d ${D}${webos_servicesdir}/org.ocf.webossample.occlientbasicops

    install -v -m 0755 ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/resource/csdk/stack/samples/webos/secure/occlientbasicops/* ${D}${webos_servicesdir}/org.ocf.webossample.occlientbasicops
    if [ "${IOTIVITY_TARGET_ARCH}" = "i686" ]; then
        install -v -m 0755 ${S}/out/webos/arm/release/resource/csdk/stack/samples/webos/secure/occlientbasicops/* ${D}${webos_servicesdir}/org.ocf.webossample.occlientbasicops
    fi
    # ACG configuration files
    install -d ${D}${datadir}/luna-service2/roles.d
    install -d ${D}${datadir}/luna-service2/services.d
    install -d ${D}${datadir}/luna-service2/client-permissions.d
    install -d ${D}${datadir}/luna-service2/api-permissions.d
    install -d ${D}${datadir}/luna-service2/manifests.d

    install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/occlientbasicops.service ${D}${datadir}/luna-service2/services.d
    install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/occlientbasicops.role.json ${D}${datadir}/luna-service2/roles.d
    install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/occlientbasicops.perm.json ${D}${datadir}/luna-service2/client-permissions.d
    install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/occlientbasicops.api.json ${D}${datadir}/luna-service2/api-permissions.d
    install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/occlientbasicops.manifest.json ${D}${datadir}/luna-service2/manifests.d
}

FILES_${PN} = "\
    ${webos_servicesdir}/org.ocf.webossample.* \
    ${datadir}/luna-service2 \
"

BBCLASSEXTEND = "native nativesdk"

# iotivity doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"

SRC_URI += "file://0001-Fix-missing-return-statement.patch"
