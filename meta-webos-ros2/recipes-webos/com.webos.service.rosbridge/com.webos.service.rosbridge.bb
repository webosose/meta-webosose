# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "webOS ROS2 bridge"
AUTHOR  = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "nodejs-native rcl rmw rosidl-generator-c rcutils"
RDEPENDS_${PN} += "luna-service2 pmloglib nodejs nodejs-module-webos-service"

WEBOS_VERSION = "1.0.0-6_fd45931f2f249aef0ebfaffe9e3d416c9614e0fa"
PR = "r1"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_machine_dep
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"
SERVICE = "com.webos.service.rosbridge"

TARGET_CFLAGS   += "-fpic"

do_install_append() {
    export HOME=${WORKDIR}
    export AMENT_PREFIX_PATH="${STAGING_EXECPREFIXDIR}"
    export CFLAGS="$CFLAGS -fpermissive"
    export CXXFLAGS="$CXXFLAGS -fpermissive"

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

    # Compile
    ${STAGING_BINDIR_NATIVE}/npm install --production --unsafe-perm --arch=${targetArch} --target-arch=${targetArch} --verbose --prefix=${S}

    # patch and recompile
    cd ${S}/node_modules/rclnodejs
    patch -p 1 -i "${THISDIR}/files/0002-fix-new-delete-mismatch.patch"
    patch -p 1 -i "${THISDIR}/files/0003-fix-memory-leak-in-subscription-messages.patch"
    ${STAGING_BINDIR_NATIVE}/npm rebuild --production --unsafe-perm --arch=${targetArch} --target-arch=${targetArch} --verbose --prefix=${S}

    # prepare-install
    rm -fr ${S}/node_modules/rclnodejs/test/
    rm -fr ${S}/node_modules/rclnodejs/scripts/
    rm -fr ${S}/node_modules/rclnodejs/src/

    # Install
    install -d ${D}${webos_servicesdir}/${SERVICE}
    cp -R -v ${S}/node_modules ${D}${webos_servicesdir}/${SERVICE}
}

FILES_${PN} += "${prefix}"
