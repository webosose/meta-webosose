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

PR = "r5"

inherit python3native

export PYTHON = "python3"

do_compile_prepend() {
    OCTBDIR="${STAGING_INCDIR}/iotivity/resource"
    export OCTBSTACK_CFLAGS="-I${OCTBDIR} -I${OCTBDIR}/stack -I${OCTBDIR}/ocrandom -I${STAGING_INCDIR}/iotivity/c_common -DROUTING_EP -DRD_CLIENT -DRD_SERVER -D__WITH_DTLS__"
    export OCTBSTACK_LIBS="-loctbstack -lresource_directory"
    export CFLAGS="$CFLAGS -fPIC"
    export CXXFLAGS="$CXXFLAGS -fPIC"
}

do_compile () {
    # changing the home directory to the working directory, the .npmrc will be created in this directory
    export HOME=${WORKDIR}

    ${STAGING_BINDIR_NATIVE}/npm update
    ${STAGING_BINDIR_NATIVE}/npm shrinkwrap

    # does not build dev packages
    ${STAGING_BINDIR_NATIVE}/npm config set dev false

    # access npm registry using http
    ${STAGING_BINDIR_NATIVE}/npm set strict-ssl false
    ${STAGING_BINDIR_NATIVE}/npm config set registry http://registry.npmjs.org/

    # configure http proxy if neccessary
    if [ -n "${http_proxy}" ]; then
        ${STAGING_BINDIR_NATIVE}/npm config set proxy ${http_proxy}
    fi
    if [ -n "${HTTP_PROXY}" ]; then
        ${STAGING_BINDIR_NATIVE}/npm config set proxy ${HTTP_PROXY}
    fi

    # configure cache to be in working directory
    ${STAGING_BINDIR_NATIVE}/npm set cache ${WORKDIR}/npm_cache

    # clear local cache prior to each compile
    ${STAGING_BINDIR_NATIVE}/npm cache clear --force

    case ${TARGET_ARCH} in
        i?86) targetArch="ia32"
            echo "targetArch = 32"
            ;;
        x86_64) targetArch="x64"
            echo "targetArch = 64"
            ;;
        arm) targetArch="arm"
            ;;
        aarch64) targetArch="arm64"
            ;;
        mips) targetArch="mips"
            ;;
        sparc) targetArch="sparc"
            ;;
        *) echo "unknown architecture"
           exit 1
            ;;
    esac

    # Compile and install node modules in source directory
    ${STAGING_BINDIR_NATIVE}/npm --arch=${targetArch} --production --verbose install
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

FILES_${PN} = "${libdir}/node_modules/iotivity-node/ \
"

# COMPATIBLE_MACHINE is set because iotivity on which the iotivity-node doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"
