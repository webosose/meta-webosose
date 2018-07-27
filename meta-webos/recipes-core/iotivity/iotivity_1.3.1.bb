# Imported from https://git.yoctoproject.org/cgit/cgit.cgi/meta-oic/commit/recipes-core/iotivity/iotivity_1.3.1.bb?id=52de3d616802a7738e5bfef4bdd7b225c1b062fd
# with added COMPATIBLE_MACHINE restriction
# Modification for webOS
# - Add luna-service and pbnjson on DEPENDS
# - Modify TARGET_OS from "yocto" to "webOS"
# - Modify scons build argument
# - Install webOS specific sample services instead of original one
# - Install ACG configuration files for webOS specific sample services
# - Inherit webos_enactjs_app to build enact sample application for IoTivity
# - Do not install resources not built for TARGET_OS=webos
# - Add app icon for IoTivity Sampler application
# - Fix bug that IoTivity Sampler is not closed by 'x' button

SUMMARY = "IoTivity framework and SDK sponsored by the Open Connectivity Foundation."
DESCRIPTION = "IoTivity is an open source software framework enabling seamless device-to-device connectivity to address the emerging needs of the Internet of Things."
HOMEPAGE = "https://www.iotivity.org/"
DEPENDS = "boost virtual/gettext chrpath-replacement-native expat openssl util-linux curl glib-2.0 glib-2.0-native"
DEPENDS += "sqlite3 luna-service2 libpbnjson python-scons-native"

EXTRANATIVEPATH += "chrpath-native"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=22bf216f3077c279aed7b36b1fa9e6d1"

branch_iotivity ?= "1.3-rel"
SRCREV ?= "633dc231b8d9967520627528a92506efca7cebcd"
url_iotivity ?= "git://github.com/iotivity/iotivity.git;destsuffix=${S};branch=${branch_iotivity};protocol=http"
SRC_URI += "${url_iotivity}"

url_tinycbor = "git://github.com/01org/tinycbor.git"
SRCREV_tinycbor = "31c7f81d45d115d2007b1c881cbbd3a19618465c"
SRC_URI += "${url_tinycbor};name=tinycbor;destsuffix=${S}/extlibs/tinycbor/tinycbor;protocol=http"

url_gtest = "https://github.com/google/googletest/archive/release-1.7.0.zip"
SRC_URI[gtest.md5sum] = "ef5e700c8a0f3ee123e2e0209b8b4961"
SRC_URI[gtest.sha256sum] = "b58cb7547a28b2c718d1e38aee18a3659c9e3ff52440297e965f5edffe34b6d0"
SRC_URI += "${url_gtest};name=gtest;subdir=${BP}/extlibs/gtest"

url_hippomocks = "git://github.com/dascandy/hippomocks.git"
SRCREV_hippomocks = "dca4725496abb0e41f8b582dec21d124f830a8e5"
SRC_URI += "${url_hippomocks};name=hippomocks;destsuffix=${S}/extlibs/hippomocks/hippomocks;protocol=http"

SRCREV_mbedtls = "85c2a928ed352845793db000e78e2b42c8dcf055"
url_mbedtls="git://github.com/ARMmbed/mbedtls.git"
SRC_URI += "${url_mbedtls};name=mbedtls;destsuffix=${S}/extlibs/mbedtls/mbedtls;protocol=http"

url_rapidjson = "git://github.com/miloyip/rapidjson.git"
SRCREV_rapidjson = "3d5848a7cd3367c5cb451c6493165b7745948308"
SRC_URI += "${url_rapidjson};name=rapidjson;;nobranch=1;destsuffix=${S}/extlibs/rapidjson/rapidjson;protocol=http"

SRC_URI += "file://0001-Add-webos-into-the-target-OS-list.patch \
    file://oic_svr_db_server.dat \
    file://oic_svr_db_client.dat \
    file://icon.png \
"

PR = "r1"

inherit scons pkgconfig webos_enactjs_app webos_component

WEBOS_ENACTJS_PROJECT_PATH ??= "./resource/csdk/stack/samples/webos/com.example.app.iotivity"
WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE = "false"
WEBOS_ENACTJS_APP_ID = "com.example.app.iotivity"

IOTIVITY_BIN_DIR = "${webos_servicesdir}/${PN}"
IOTIVITY_BIN_DIR_D = "${D}${IOTIVITY_BIN_DIR}"

# This is wrong and should respect TARGET_ARCH set in OE build
# but as it's now, it always creates the files in out/webos/arm/release
# even when building for e.g. x86 with qemux86 MACHINE
# file BUILD/work/qemux86-webos-linux/iotivity/1.3.1-r1/iotivity-1.3.1/out/webos/arm/release/libresource_directory.so
# BUILD/work/qemux86-webos-linux/iotivity/1.3.1-r1/iotivity-1.3.1/out/webos/arm/release/libresource_directory.so: ELF 32-bit LSB  shared object, Intel 80386, version 1 (SYSV), dynamically linked, BuildID[sha1]=51acad0ee3e98648b762722e1fcf6285861006cf, not strippe
# so we need to keep "arm" until it's fixed in SConscript, otherwise qemux86 builds will fail in do_install
# currently we cannot use
# IOTIVITY_TARGET_ARCH = "${TARGET_ARCH}"
# like the recipe in meta-oic does, because then scons fails with:
# scons: *** Invalid value for option TARGET_ARCH: i586.  Valid values are: ['arm']
IOTIVITY_TARGET_ARCH = "${TARGET_ARCH}"

EXTRA_OESCONS += " \
    TARGET_OS=webos TARGET_TRANSPORT=IP RELEASE=1 \
    TARGET_ARCH=${IOTIVITY_TARGET_ARCH} \
    VERBOSE=1 \
    SECURED=1 \
    RD_MODE=all \
    LOGGING=true LOG_LEVEL=INFO \
"

do_compile_append() {
    export PKG_CONFIG_PATH="${PKG_CONFIG_PATH}"
    export PKG_CONFIG="PKG_CONFIG_SYSROOT_DIR=\"${PKG_CONFIG_SYSROOT_DIR}\" pkg-config"
    export LD_FLAGS="${LD_FLAGS}"
    scons_do_compile
}

make_dir() {
    install -d $1
}

copy_file() {
    install -c -m 444 $1 $2
}

copy_exec() {
    install -c -m 555 $1 $2
}

copy_file_recursive() {
    cd $1 && find . -type d -exec install -d $2/"{}" \;
    cd $1 && find . -type f -exec install -c -m 444 "{}" $2/"{}" \;
}

copy_exec_recursive() {
    cd $1 && find . -executable -exec install -c -m 555 "{}" $2/"{}" \;
}

do_install_append() {
    make_dir ${D}${libdir}
    #Resource
    #C++ APIs
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/liboc.so ${D}${libdir}
    if ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', 'false', 'true', d)}; then
        copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libocprovision.so ${D}${libdir}
        copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libocpmapi.so ${D}${libdir}
    fi

    #Logger
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/liboc_logger.so ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/liboc_logger_core.so ${D}${libdir}

    #CSDK Shared
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libconnectivity_abstraction.so ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/liboctbstack.so ${D}${libdir}

    #CSDK Static
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libconnectivity_abstraction.a ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libcoap.a ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/liboctbstack.a ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libc_common.a ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libocsrm.a ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libroutingmanager.a ${D}${libdir}

    #Resource CSDK Apps
    if ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', 'true', 'false', d)}; then
        make_dir ${D}${webos_servicesdir}/org.ocf.webossample.ocserver
        make_dir ${D}${webos_servicesdir}/org.ocf.webossample.occlient
        copy_exec ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/resource/csdk/stack/samples/webos/unsecure/ocserver/ocserver ${D}${webos_servicesdir}/org.ocf.webossample.ocserver
        copy_exec ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/resource/csdk/stack/samples/webos/unsecure/occlient/occlient ${D}${webos_servicesdir}/org.ocf.webossample.occlient
    fi

    if ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', 'false', 'true', d)}; then
        make_dir ${D}${webos_servicesdir}/org.ocf.webossample.ocserverbasicops
        make_dir ${D}${webos_servicesdir}/org.ocf.webossample.occlientbasicops
        copy_exec ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/resource/csdk/stack/samples/webos/secure/ocserverbasicops/ocserverbasicops ${D}${webos_servicesdir}/org.ocf.webossample.ocserverbasicops
        copy_exec ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/resource/csdk/stack/samples/webos/secure/occlientbasicops/occlientbasicops ${D}${webos_servicesdir}/org.ocf.webossample.occlientbasicops

        install -v -m 0644 ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/resource/csdk/stack/samples/linux/secure/oic_svr_db_server.dat ${D}${webos_servicesdir}/org.ocf.webossample.ocserverbasicops
        install -v -m 0644 ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/resource/csdk/stack/samples/linux/secure/oic_svr_db_client_nondevowner.dat ${D}${webos_servicesdir}/org.ocf.webossample.occlientbasicops
        install -v -m 0644 ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/resource/csdk/stack/samples/linux/secure/oic_svr_db_client_devowner.dat ${D}${webos_servicesdir}/org.ocf.webossample.occlientbasicops
    fi

    #Resource Tests
    make_dir ${IOTIVITY_BIN_DIR_D}/tests/resource
    if ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', 'false', 'true', d)}; then
        copy_exec ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/resource/csdk/security/tool/json2cbor ${IOTIVITY_BIN_DIR_D}/
    fi

    #Service Components
    #Resource container
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/librcs_container.so ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/librcs_container.a ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libHueBundle.so ${D}${libdir}

    #Resource container sample apps
    make_dir ${IOTIVITY_BIN_DIR_D}/examples/service/resource-container/
    copy_exec ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/service/resource-container/ContainerSample ${IOTIVITY_BIN_DIR_D}/examples/service/resource-container/
    copy_exec ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/service/resource-container/ContainerSampleClient ${IOTIVITY_BIN_DIR_D}/examples/service/resource-container/

    #Resource encapsulation
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/librcs_client.so ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/librcs_client.a ${D}${libdir}

    #Resource encapsulation common
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/librcs_common.so ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/librcs_common.a ${D}${libdir}

    #Resource encapsulation server builder
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/librcs_server.so ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/librcs_server.a ${D}${libdir}

    #Resource directory
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libresource_directory.a ${D}${libdir}
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libresource_directory.so ${D}${libdir}

    #Resource directory samples
    if ${@bb.utils.contains('EXTRA_OESCONS', 'RD_MODE=CLIENT,SERVER', 'true', 'false', d)}; then
        make_dir ${IOTIVITY_BIN_DIR_D}/examples/service/resource-directory
        copy_exec_recursive ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/service/resource-directory/samples ${IOTIVITY_BIN_DIR_D}/examples/service/resource-directory
    fi

    #Install platform_features.h for build compatibility with iotivity-node
    make_dir ${D}${includedir}/iotivity/resource/stack/
    copy_file ${S}/resource/c_common/platform_features.h ${D}${includedir}/iotivity/resource
    copy_file ${S}/resource/c_common/platform_features.h ${D}${includedir}/iotivity/resource/stack

    #Adapt unaligned pkconfig (transitionnal)
    sed -e 's|^prefix=.*|prefix=/usr|g' -i ${S}/iotivity.pc
    make_dir ${D}${libdir}/pkgconfig/
    copy_file ${S}/iotivity.pc ${D}${libdir}/pkgconfig/

    #Installed headers
    make_dir ${D}${includedir}
    copy_file_recursive \
       ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/include \
       ${D}${includedir}/iotivity
    ln -s iotivity/resource ${D}${includedir}/resource
    ln -s iotivity/service ${D}${includedir}/service
    ln -s iotivity/c_common ${D}${includedir}/c_common

    find "${D}" -type f -perm /u+x -exec chrpath -d "{}" \;
    find "${D}" -type f -iname "lib*.so" -exec chrpath -d "{}" \;

    # ACG configuration files
    install -d ${D}${datadir}/luna-service2/roles.d
    install -d ${D}${datadir}/luna-service2/services.d
    install -d ${D}${datadir}/luna-service2/client-permissions.d
    install -d ${D}${datadir}/luna-service2/api-permissions.d
    install -d ${D}${datadir}/luna-service2/manifests.d

    # ACG configuration for secured native samples
    if ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', 'false', 'true', d)}; then
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/ocserverbasicops.service ${D}${datadir}/luna-service2/services.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/ocserverbasicops.role.json ${D}${datadir}/luna-service2/roles.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/occlientbasicops.service ${D}${datadir}/luna-service2/services.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/occlientbasicops.role.json ${D}${datadir}/luna-service2/roles.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/ocserverbasicops.perm.json ${D}${datadir}/luna-service2/client-permissions.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/occlientbasicops.perm.json ${D}${datadir}/luna-service2/client-permissions.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/ocserverbasicops.api.json ${D}${datadir}/luna-service2/api-permissions.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/occlientbasicops.api.json ${D}${datadir}/luna-service2/api-permissions.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/ocserverbasicops.manifest.json ${D}${datadir}/luna-service2/manifests.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/secure/files/sysbus/occlientbasicops.manifest.json ${D}${datadir}/luna-service2/manifests.d
    fi

    # ACG configuration for unsecured native samples
    if ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', 'true', 'false', d)}; then
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/unsecure/files/sysbus/ocserver.service ${D}${datadir}/luna-service2/services.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/unsecure/files/sysbus/ocserver.role.json ${D}${datadir}/luna-service2/roles.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/unsecure/files/sysbus/occlient.service ${D}${datadir}/luna-service2/services.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/unsecure/files/sysbus/occlient.role.json ${D}${datadir}/luna-service2/roles.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/unsecure/files/sysbus/ocserver.perm.json ${D}${datadir}/luna-service2/client-permissions.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/unsecure/files/sysbus/occlient.perm.json ${D}${datadir}/luna-service2/client-permissions.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/unsecure/files/sysbus/ocserver.api.json ${D}${datadir}/luna-service2/api-permissions.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/unsecure/files/sysbus/occlient.api.json ${D}${datadir}/luna-service2/api-permissions.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/unsecure/files/sysbus/ocserver.manifest.json ${D}${datadir}/luna-service2/manifests.d
        install -c -m 555 ${S}/resource/csdk/stack/samples/webos/unsecure/files/sysbus/occlient.manifest.json ${D}${datadir}/luna-service2/manifests.d
    fi

    # ACG configuration for node samples
    install -d ${D}${webos_servicesdir}/com.example.service.iotivity.server
    install -v -m 0755 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/*.* ${D}${webos_servicesdir}/com.example.service.iotivity.server/
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.service ${D}${datadir}/luna-service2/services.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.role.json ${D}${datadir}/luna-service2/roles.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.perm.json ${D}${datadir}/luna-service2/client-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.api.json ${D}${datadir}/luna-service2/api-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.server/files/sysbus/com.example.service.iotivity.server.manifest.json ${D}${datadir}/luna-service2/manifests.d

    install -d ${D}${webos_servicesdir}/com.example.service.iotivity.client
    install -v -m 0755 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/*.* ${D}${webos_servicesdir}/com.example.service.iotivity.client/
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.service ${D}${datadir}/luna-service2/services.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.role.json ${D}${datadir}/luna-service2/roles.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.perm.json ${D}${datadir}/luna-service2/client-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.api.json ${D}${datadir}/luna-service2/api-permissions.d
    install -v -m 0644 ${S}/resource/csdk/stack/samples/webos/com.example.app.iotivity/services/com.example.service.iotivity.client/files/sysbus/com.example.service.iotivity.client.manifest.json ${D}${datadir}/luna-service2/manifests.d

    # dat files for node samples
    install -v -m 0644 ${WORKDIR}/oic_svr_db_server.dat ${D}${webos_servicesdir}/com.example.service.iotivity.server/
    install -v -m 0644 ${WORKDIR}/oic_svr_db_client.dat ${D}${webos_servicesdir}/com.example.service.iotivity.client/
    install -v -m 0644 ${WORKDIR}/icon.png ${D}${webos_applicationsdir}/com.example.app.iotivity/
}

# IOTIVITY packages:
# Resource: iotivity-resource, iotivity-resource-samples
# Service: iotivity-service, iotivity-service-samples
# Plugins: iotivity-plugins-samples
# Node app: iotivity-node-ap
# Tests: iotivity-tests
# Misc: iotivity-tools

FILES_SOLIBSDEV = ""

FILES_${PN}-resource = "\
    ${libdir}/libconnectivity_abstraction.so \
    ${libdir}/liboc.so \
    ${libdir}/liboctbstack.so \
    ${libdir}/liboc_logger.so \
    ${libdir}/liboc_logger_core.so \
    ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', '', '${libdir}/libocprovision.so', d)} \
    ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', '', '${libdir}/libocpmapi.so', d)} \
    ${libdir}/libresource_directory.so \
"

FILES_${PN}-resource-samples = "\
    ${webos_servicesdir}/org.ocf.webossample.* \
    ${datadir}/luna-service2 \
"

FILES_${PN}-plugins-samples = "\
    ${IOTIVITY_BIN_DIR}/examples/plugins \
"

FILES_${PN}-service = "\
    ${libdir}/libBMISensorBundle.so \
    ${libdir}/libDISensorBundle.so \
    ${libdir}/librcs_server.so \
    ${libdir}/librcs_common.so \
    ${libdir}/librcs_container.so \
    ${libdir}/libHueBundle.so \
    ${libdir}/libESEnrolleeSDK.so \
    ${libdir}/libESMediatorRich.so \
    ${libdir}/libnotification_consumer.so \
    ${libdir}/libnotification_provider.so \
    ${libdir}/librcs_client.so \
    ${libdir}/libTestBundle.so \
"

FILES_${PN}-service-samples = "\
    ${IOTIVITY_BIN_DIR}/examples/service \
"

FILES_${PN}-tests = "\
    ${IOTIVITY_BIN_DIR}/tests \
    ${libdir}/liboctbstack_test.so \
"

FILES_${PN}-tools = "\
    ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', '', '${IOTIVITY_BIN_DIR}/json2cbor', d)} \
"

FILES_${PN}-node-app = "\
    ${webos_applicationsdir} \
    ${webos_servicesdir} \
"

PACKAGE_BEFORE_PN += "${PN}-tests ${PN}-plugins-samples ${PN}-resource ${PN}-resource-samples ${PN}-service ${PN}-service-samples ${PN}-tools ${PN}-node-app"
ALLOW_EMPTY_${PN} = "1"
RRECOMMENDS_${PN} += "${PN}-resource ${PN}-service"
RDEPENDS_${PN}-plugins-samples += "${PN}-resource"
RDEPENDS_${PN}-resource-samples += "${PN}-resource"
RDEPENDS_${PN}-tests += "${PN}-resource ${PN}-service"
RDEPENDS_${PN}-service-samples += "${PN}-service ${PN}-resource"
RDEPENDS_${PN}-service += "${PN}-resource"
RDEPENDS_${PN}-tools += "${PN}-resource"

BBCLASSEXTEND = "native nativesdk"

# iotivity doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"
