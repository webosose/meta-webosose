# Copyright (c) 2022 LG Electronics, Inc.

inherit python3-dir

PACKAGE_BEFORE_PN += "${PN}-${PYTHON_PN}"

RDEPENDS:${PN}-${PYTHON_PN} = "${PN}"

do_install:append() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    cp -rf ${S}/python/flatbuffers ${D}${PYTHON_SITEPACKAGES_DIR}
}

FILES:${PN}-dev = ""

INSANE_SKIP:${PN} = "dev-so"
INSANE_SKIP:${PN}-dev += "dev-elf"
FILES:${PN} += "${libdir}/* ${includedir}/*"
FILES:${PN}-${PYTHON_PN} = "${PYTHON_SITEPACKAGES_DIR}"


