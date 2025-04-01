# Copyright (c) 2024-2025 LG Electronics, Inc.

SUMMARY = "A loctool plugin that knows how to process JSON resource files"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://node_modules/ilib/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PR = "r0"

SRC_URI = " \
    npm://registry.npmjs.org/;package=ilib-loctool-webos-json-resource;version=${PV} \
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
