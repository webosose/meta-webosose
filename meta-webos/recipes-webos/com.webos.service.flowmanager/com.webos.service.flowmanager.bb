# Copyright (c) 2019-2022 LG Electronics, Inc.

SUMMARY = "webOS Flow manager"
AUTHOR  = "Namsu Kim <namsu81.kim@lge.com>"
SECTION = "webos/extended-service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=66c8021042d90a78e662c2cf62e2ee13 \
"

RDEPENDS:${PN} += "luna-service2 pmloglib nodejs nodejs-module-webos-service nodejs-module-node-red"

inherit systemd
inherit webos_cmake
inherit webos_system_bus
inherit webos_enhanced_submissions
inherit webos_npm_env

require flowmanager.inc
PR = "r7"

# The same restrition as nodejs (and nodejs-module-node-red)
COMPATIBLE_MACHINE:armv4 = "(!.*armv4).*"
COMPATIBLE_MACHINE:armv5 = "(!.*armv5).*"
COMPATIBLE_MACHINE:mips64 = "(!.*mips64).*"

DEPENDS:append = " nodejs-native"

S = "${WORKDIR}/git"

# Remove --production, because that causes
# http://gecko.lge.com/Errors/Details/119723
# > com.webos.service.flowmanager@1.0.0 webpack TOPDIR/BUILD/work/raspberrypi4-webos-linux-gnueabi/com.webos.service.flowmanager/1.0.0-7-r6/git
# > ./node_modules/webpack/bin/webpack.js --mode=development
# sh: 1: ./node_modules/webpack/bin/webpack.js: not found
# here
WEBOS_NPM_INSTALL_FLAGS = "--arch=${WEBOS_NPM_ARCH} --target_arch=${WEBOS_NPM_ARCH} --without-ssl --insecure --no-optional --verbose"

do_compile:append() {
    cd ${S}
    ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install
    ${WEBOS_NPM_BIN} run webpack
}

FILES:${PN} += "${webos_servicesdir}"

SRC_URI += "file://0001-webpack-use-sha256-instead-of-ancient-md4-to-fix-bui.patch"
