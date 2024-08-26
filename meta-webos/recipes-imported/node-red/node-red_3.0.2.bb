DESCRIPTION = "Node-RED"
HOMEPAGE = "http://nodered.org"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=014f1a23c3da49aa929b21a96808ab22"

inherit npm

PR = "r4"

SRC_URI = "\
    https://github.com/${BPN}/${BPN}/releases/download/${PV}/${BPN}-${PV}.zip \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
    file://${BPN}.service \
"

SRC_URI[sha256sum] = "6c452646648f9e86622148eff2208fb45d2311b5339481f86b445e9e2fa215c5"

S = "${WORKDIR}/${BPN}"

do_install:append() {
    # Service
    install -d ${D}${systemd_system_unitdir}/
    install -m 0644 ${UNPACKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/

    # Remove hardware specific files
    rm -v ${D}/${bindir}/${BPN}-pi
    rm -rvf ${D}${nonarch_libdir}/node_modules/${BPN}/bin

    # Remove tmp files
    rm -rvf ${D}${nonarch_libdir}/node_modules/${BPN}/node_modules/bcrypt/build-tmp-napi-v3
    rm -rvf ${D}${nonarch_libdir}/node_modules/${BPN}/node_modules/bcrypt/node-addon-api
}

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "${BPN}.service"

FILES:${PN} += "\
    ${systemd_unitdir} \
"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# fails only with 32bit MACHINEs it seems
# http://gecko.lge.com:8000/Errors/Details/911532
# ERROR: QA Issue: node-red: ELF binary /usr/lib/node_modules/node-red/node_modules/bcrypt/lib/binding/napi-v3/bcrypt_lib.node has relocations in .text [textrel]
INSANE_SKIP:${PN} += "textrel"
