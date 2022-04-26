# Copyright (c) 2018-2022 LG Electronics, Inc.

SUMMARY = "Loadable node-red module for nodejs services"
AUTHOR = "Tirthadeep Roy <tirthadeep.roy@lge.com>"
SECTION = "webOS/modules"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d6f37569f5013072e9490d2194d10ae6"

PR = "r3"

DEPENDS += "nodejs-native"
RDEPENDS:${PN} = "nodejs"

# The same restrition as nodejs
COMPATIBLE_MACHINE:armv4 = "(!.*armv4).*"
COMPATIBLE_MACHINE:armv5 = "(!.*armv5).*"
COMPATIBLE_MACHINE:mips64 = "(!.*mips64).*"

# If uncomment under 'TODO' on do_install step,
# this package will provide much more things actually. (e.g. express)

SRC_URI = "https://github.com/node-red/node-red/releases/download/${PV}/node-red-${PV}.zip"
SRC_URI[md5sum] = "bdcd269c789f93717c507008bbe69502"
SRC_URI[sha256sum] = "796cf5ce6446a9a77299b88c25f94703a5977ff90b067ed68c50e5c6bb7f321f"

S = "${WORKDIR}/node-red-${PV}"

inherit webos_npm_env

do_compile() {
    ${WEBOS_NPM_BIN} ${WEBOS_NPM_INSTALL_FLAGS} install
}

do_install() {
    install -d ${D}${libdir}/node_modules/node-red
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${libdir}/node_modules/node-red

    # TODO Move dependent packages to top node_modules dir
    # mv ${TARGET_DIR}/node-red/node_modules/* ${TARGET_DIR}
}

FILES:${PN} += "${libdir}/node_modules"

# ERROR: QA Issue: /usr/lib/node_modules/node-red/nodes/core/hardware/nrgpio contained in package nodejs-module-node-red requires /bin/bash, but no providers found in RDEPENDS:nodejs-module-node-red? [file-rdeps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# From 0.19.0 https://github.com/node-red/node-red/commit/4bcf13cb58869902e3d62294af91eeece5c93497
SRC_URI += "file://python3.patch"
