# Copyright (c) 2018-2023 LG Electronics, Inc.

SUMMARY = "A module for nodejs usocket"
HOMEPAGE = "https://github.com/jhs67/usocket#readme"
SECTION = "webos/nodejs/module"
LICENSE = "ISC & MIT"
LICENSE:${PN} = "ISC"
LICENSE:${PN}-bindings = "MIT"
LICENSE:${PN}-file-uri-to-path = "MIT"
LICENSE:${PN}-nan = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5717bc308dbc03edd5e758d11c5bab65 \
    file://node_modules/bindings/LICENSE.md;md5=471723f32516f18ef36e7ef63580e4a8 \
    file://node_modules/file-uri-to-path/LICENSE;md5=9513dc0b97137379cfabc81b60889174 \
    file://node_modules/nan/LICENSE.md;md5=3952ff1c51e4ebe5b12c1bc501de4683 \
"

inherit npm

PR = "r11"

SRC_URI = "\
    npm://registry.npmjs.org/;package=usocket;version=${PV} \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
"

S = "${WORKDIR}/npm"
