# Copyright (c) 2024-2025 LG Electronics, Inc.

SUMMARY = "A loctool plugin that knows how to process ts resource files"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Apache-2.0" below, you will need to check them yourself:
#
# NOTE: multiple licenses have been detected; they have been separated with &
# in the LICENSE value for now since it is a reasonable assumption that all
# of the licenses apply. If instead there is a choice between the multiple
# licenses then you should change the value to separate the licenses with |
# instead of &. If there is any doubt, check the accompanying documentation
# to determine which situation is applicable.
LICENSE = "Apache-2.0 & ISC & MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://node_modules/ilib/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
                    file://node_modules/sax/LICENSE;md5=5f49410228c16917dacc5eab921004cb \
                    file://node_modules/xml-js/LICENSE;md5=7500faf5d0f9d9fa8a6846c3a9d4d2df \
                    file://node_modules/pretty-data/README.md;md5=cb335af5033fff681cde255b64c6999a"

PR = "r0"

SRC_URI = " \
    npm://registry.npmjs.org/;package=ilib-loctool-webos-ts-resource;version=${PV} \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
"

S = "${WORKDIR}/npm"

RDEPENDS:${PN} = "loctool-native"

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
LICENSE:${PN}-ilib = "Apache-2.0"
LICENSE:${PN}-pretty-data = "MIT"
LICENSE:${PN}-sax = "ISC"
LICENSE:${PN}-xml-js = "MIT"
