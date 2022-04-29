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
# - Add a fix for missing return statements

SUMMARY = "IoTivity framework and SDK sponsored by the Open Connectivity Foundation."
DESCRIPTION = "IoTivity is an open source software framework enabling seamless device-to-device connectivity to address the emerging needs of the Internet of Things."
HOMEPAGE = "https://www.iotivity.org/"
DEPENDS = "boost virtual/gettext chrpath-replacement-native expat openssl util-linux curl glib-2.0 glib-2.0-native"
DEPENDS += "sqlite3 luna-service2 libpbnjson"

EXTRANATIVEPATH += "chrpath-native"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = " \
    git://github.com/iotivity/iotivity.git;branch=webos;protocol=https;name=main \
    git://github.com/intel/tinycbor.git;destsuffix=git/extlibs/tinycbor/tinycbor;protocol=https;name=tinycbor;branch=master \
    git://github.com/google/googletest.git;destsuffix=git/extlibs/gtest/googletest-release-1.7.0;protocol=https;nobranch=1;name=gtest \
    git://github.com/dascandy/hippomocks.git;destsuffix=git/extlibs/hippomocks/hippomocks;protocol=https;name=hippomocks;branch=master \
    git://github.com/ARMmbed/mbedtls.git;destsuffix=git/extlibs/mbedtls/mbedtls;protocol=https;branch=archive/mbedtls-2.4;name=mbedtls \
    git://github.com/miloyip/rapidjson.git;destsuffix=git/extlibs/rapidjson/rapidjson;protocol=https;name=rapidjson;branch=master \
    git://github.com/dthaler/libcoap.git;destsuffix=git/extlibs/libcoap/libcoap;protocol=https;branch=develop;name=libcoap \
    file://0001-hippomocks-fix-build-for-MIPS.patch;patchdir=extlibs/hippomocks/hippomocks \
    file://0001-OCApi.h-prevent-multiple-definitions-of-OC-detail-oc.patch \
    file://0002-Fix-segmentation-fault.patch \
    file://0002-Add-os_arch-x86_64.patch \
    file://0001-mbedtls-tinycbor-add-DONT_CHECK_GIT_TAGS-env-variabl.patch \
"
SRCREV_main = "255060695e4de0e8f357aeab6e3e2c46e3c68bac"
SRCREV_tinycbor = "ae64a3d9da39f3bf310b9a7b38427c096d8bcd43"
SRCREV_gtest = "c99458533a9b4c743ed51537e25989ea55944908"
SRCREV_hippomocks = "dca4725496abb0e41f8b582dec21d124f830a8e5"
SRCREV_mbedtls = "59ae96f167a19f4d04dc6db61f6587b37ccd429f"
SRCREV_rapidjson = "9dfc437477e2b9a351634e8249a9c18bfc81f136"
SRCREV_libcoap = "fa8b2b9d2c8215c8579bc05132a6eae1123ae4fd"

SRCREV_FORMAT = "main"
do_fetch[vardeps] = "SRCREV_main SRCREV_tinycbor SRCREV_gtest SRCREV_hippomocks SRCREV_mbedtls SRCREV_rapidjson SRCREV_libcoap"

S = "${WORKDIR}/git"

PR = "r8"
PV = "1.3.99+git${SRCPV}"

inherit scons pkgconfig webos_filesystem_paths

IOTIVITY_BIN_DIR = "${webos_servicesdir}/${PN}"
IOTIVITY_BIN_DIR_D = "${D}${IOTIVITY_BIN_DIR}"

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

    #Resource Tests
    make_dir ${IOTIVITY_BIN_DIR_D}/tests/resource
    make_dir ${D}${sbindir}
    if ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', 'false', 'true', d)}; then
        copy_exec ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/resource/csdk/security/tool/json2cbor ${D}${sbindir}
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

    #Easy setup
    copy_file ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/libESEnrolleeSDK.so ${D}${libdir}

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
       ${S}/out/webos/${IOTIVITY_TARGET_ARCH}/release/deploy/include \
       ${D}${includedir}/iotivity
    ln -s iotivity/resource ${D}${includedir}/resource
    ln -s iotivity/service ${D}${includedir}/service
    ln -s iotivity/c_common ${D}${includedir}/c_common

    find "${D}" -type f -perm /u+x -exec chrpath -d "{}" \;
    find "${D}" -type f -iname "lib*.so" -exec chrpath -d "{}" \;
}

# IOTIVITY packages:
# Resource: iotivity-resource
# Service: iotivity-service, iotivity-service-samples
# Plugins: iotivity-plugins-samples
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
    ${@bb.utils.contains('EXTRA_OESCONS', 'SECURED=0', '', '${sbindir}/json2cbor', d)} \
"

PACKAGE_BEFORE_PN += "${PN}-tests ${PN}-plugins-samples ${PN}-resource ${PN}-service ${PN}-service-samples ${PN}-tools"
ALLOW_EMPTY_${PN} = "1"
RRECOMMENDS_${PN} += "${PN}-resource ${PN}-service"
RDEPENDS_${PN}-plugins-samples += "${PN}-resource"
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

# http://caprica.lgsvl.com:8080/Errors/Details/1300370
# iotivity/1.3.99+gitAUTOINC+179f1820ff-r1/recipe-sysroot/usr/include/features.h:381:4: error: #warning _FORTIFY_SOURCE requires compiling with optimization (-O) [-Werror=cpp]
#  warning _FORTIFY_SOURCE requires compiling with optimization (-O)
#    ^~~~~~~
# cc1: all warnings being treated as error
lcl_maybe_fortify = ""

# http://caprica.lgsvl.com:8080/Errors/Details/1303314
# resource/csdk/stack/samples/webos/secure/occlientbasicops/occlientbasicops.cpp:976:31: error: format not a string literal and no format arguments [-Werror=format-security]
#         printf (error->message);
#                               ^
SECURITY_STRINGFORMAT = ""

SRC_URI += "file://0001-Fix-missing-return-statement.patch"
