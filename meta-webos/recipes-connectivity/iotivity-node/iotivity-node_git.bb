# Imported from https://github.com/intel/iot-web-layers/blob/master/meta-iot-web/recipes-web/iotivity-node/iotivity-node_1.2.1-1.bb
# Modification for webOS
# - SRCREV was updated to c6aab8e6126c06516090dd13e812fee0d16b8cc7 (iotivity-node 1.3.1)
# - Secured build option is added
# - Let the recipe do not purge the build scripts when rebuild

SUMMARY = "This project provides iotivity node.js bindings."
HOMEPAGE = "https://github.com/otcshare/iotivity-node"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://index.js;beginline=1;endline=13;md5=bafd0234b0f43def6ab0bced96530c42"

DEPENDS = "nodejs-native glib-2.0 iotivity"
RDEPENDS_${PN} += "iotivity-resource iotivity-tools"

SRC_URI = "git://github.com/otcshare/iotivity-node.git;protocol=https"
SRCREV = "c6aab8e6126c06516090dd13e812fee0d16b8cc7"
SRC_URI += "file://0001-Add-new-api-for-register-custom-cbor-files.patch \
    file://0002-Build-Do-not-purge-build-scripts-after-build.patch \
"

PV = "1.3.1+git${SRCPV}"

S = "${WORKDIR}/git"

PR = "r7"

inherit python3native
inherit webos_npm_env

export PYTHON = "python3"

do_compile_prepend() {
    OCTBDIR="${STAGING_INCDIR}/iotivity/resource"
    export OCTBSTACK_CFLAGS="-I${OCTBDIR} -I${OCTBDIR}/stack -I${OCTBDIR}/ocrandom -I${STAGING_INCDIR}/iotivity/c_common -DROUTING_EP -DRD_CLIENT -DRD_SERVER -D__WITH_DTLS__"
    export OCTBSTACK_LIBS="-loctbstack -lresource_directory"
    export CFLAGS="$CFLAGS -fPIC"
    export CXXFLAGS="$CXXFLAGS -fPIC"
}

do_compile () {
    ${WEBOS_NPM_BIN} update
    ${WEBOS_NPM_BIN} shrinkwrap

    # Compile and install node modules in source directory
    ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install
}

do_install () {
    install -d ${D}${libdir}/node_modules/iotivity-node/
    install -m 0644 ${S}/AUTHORS.txt ${D}${libdir}/node_modules/iotivity-node/AUTHORS.txt
    install -m 0644 ${S}/index.js ${D}${libdir}/node_modules/iotivity-node/index.js
    install -m 0644 ${S}/lowlevel.js ${D}${libdir}/node_modules/iotivity-node/lowlevel.js
    install -m 0644 ${S}/README.md ${D}${libdir}/node_modules/iotivity-node/README.md
    install -m 0644 ${S}/package.json ${D}${libdir}/node_modules/iotivity-node/package.json

    cp -r ${S}/lib ${D}${libdir}/node_modules/iotivity-node/
    cp -r ${S}/node_modules/ ${D}${libdir}/node_modules/iotivity-node/

    install -d ${D}${libdir}/node_modules/iotivity-node/build/Release/
    install -m 0755 ${S}/build/Release/iotivity.node ${D}${libdir}/node_modules/iotivity-node/build/Release/
}

# /usr/lib/node_modules/iotivity-node/node_modules/uuid/benchmark/bench.sh is included in this package only _sometimes_
# but when it does get included, it rdepends on /bin/bash
# ERROR: iotivity-node-1.3.1+gitAUTOINC+c6aab8e612-r7 do_package_qa: QA Issue: /usr/lib/node_modules/iotivity-node/node_modules/uuid/benchmark/bench.sh contained in package iotivity-node requires /bin/bash, but no providers found in RDEPENDS_iotivity-node? [file-rdeps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

FILES_${PN} = "${libdir}/node_modules/iotivity-node/"

# COMPATIBLE_MACHINE is set because iotivity on which the iotivity-node doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"
