# Copyright (c) 2024-2025 LG Electronics, Inc.

SUMMARY = "json type of file handler plugin for webOS platform loctool"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# NOTE: multiple licenses have been detected; they have been separated with &
# in the LICENSE value for now since it is a reasonable assumption that all
# of the licenses apply. If instead there is a choice between the multiple
# licenses then you should change the value to separate the licenses with |
# instead of &. If there is any doubt, check the accompanying documentation
# to determine which situation is applicable.
LICENSE = "Apache-2.0 & MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://node_modules/braces/LICENSE;md5=0f64900f8f30e53054962c9f1fc3205b \
                    file://node_modules/fill-range/LICENSE;md5=0f64900f8f30e53054962c9f1fc3205b \
                    file://node_modules/ilib/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
                    file://node_modules/is-number/LICENSE;md5=0f64900f8f30e53054962c9f1fc3205b \
                    file://node_modules/micromatch/LICENSE;md5=0f64900f8f30e53054962c9f1fc3205b \
                    file://node_modules/picomatch/LICENSE;md5=abd0e25891525eb13d5a794f550a6ee4 \
                    file://node_modules/to-regex-range/LICENSE;md5=b561e0a423bedc9d9ca9c8c67d40abb9"

PR = "r0"

SRC_URI = " \
    npm://registry.npmjs.org/;package=ilib-loctool-webos-json;version=${PV} \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
"

S = "${WORKDIR}/npm"

RDEPENDS:${PN} = "loctool-native"
DEPENDS = "ilib-loctool-webos-json-resource-native"

inherit npm
inherit native

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${base_prefix}/opt/js-loctool/node_modules/${BPN}
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt/js-loctool/node_modules/${BPN}
}

SYSROOT_DIRS += "${base_prefix}/opt"

LICENSE:${PN} = "Apache-2.0"
LICENSE:${PN}-braces = "MIT"
LICENSE:${PN}-fill-range = "MIT"
LICENSE:${PN}-ilib = "Apache-2.0"
LICENSE:${PN}-is-number = "MIT"
LICENSE:${PN}-micromatch = "MIT"
LICENSE:${PN}-picomatch = "MIT"
LICENSE:${PN}-to-regex-range = "MIT"
