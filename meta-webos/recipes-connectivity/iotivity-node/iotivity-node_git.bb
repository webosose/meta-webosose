SUMMARY = "This project provides iotivity node.js bindings."
HOMEPAGE = "https://github.com/otcshare/iotivity-node"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://index.js;beginline=1;endline=13;md5=bafd0234b0f43def6ab0bced96530c42"

DEPENDS = "nodejs-native glib-2.0 iotivity"
RDEPENDS_${PN} += "iotivity-resource"

SRC_URI = "git://github.com/otcshare/iotivity-node.git;protocol=https"
SRCREV = "63587a0d365fc66ec40dc6d4354329d0c1ff092f"
PV = "1.2.1+git${SRCPV}"

S = "${WORKDIR}/git"

do_compile_prepend() {
    OCTBDIR="${STAGING_INCDIR}/iotivity/resource"
    export OCTBSTACK_CFLAGS="-I${OCTBDIR} -I${OCTBDIR}/stack -I${OCTBDIR}/ocrandom -DROUTING_EP"
    export OCTBSTACK_LIBS="-loctbstack"
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
    ${STAGING_BINDIR_NATIVE}/npm cache clear

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

    cp -r ${S}${base_libdir} ${D}${libdir}/node_modules/iotivity-node/
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
