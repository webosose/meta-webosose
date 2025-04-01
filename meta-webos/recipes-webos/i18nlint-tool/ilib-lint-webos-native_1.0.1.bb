# Copyright (c) 2024-2025 LG Electronics, Inc.

SUMMARY = "ilib-lint plugin to support webOS Platform"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# NOTE: multiple licenses have been detected; they have been separated with &
# in the LICENSE value for now since it is a reasonable assumption that all
# of the licenses apply. If instead there is a choice between the multiple
# licenses then you should change the value to separate the licenses with |
# instead of &. If there is any doubt, check the accompanying documentation
# to determine which situation is applicable.
LICENSE = "Apache-2.0 & ISC & MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://node_modules/date-format/LICENSE;md5=11e69b4aa865a34554aefa2958c837cf \
                    file://node_modules/debug/LICENSE;md5=d85a365580888e9ee0a01fb53e8e9bf0 \
                    file://node_modules/flatted/LICENSE;md5=73d317079e156478653d02207ca984da \
                    file://node_modules/fs-extra/LICENSE;md5=ea817882455c03503f7d014a8f54f095 \
                    file://node_modules/graceful-fs/LICENSE;md5=163972d49c2f7a3d3b687aeb48e9e3c9 \
                    file://node_modules/ilib-lint-common/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
                    file://node_modules/ilib-lint-common/docs/scripts/prettify/Apache-License-2.0.txt;md5=3b83ef96387f14655fc854ddc3c6bd57 \
                    file://node_modules/jsonfile/LICENSE;md5=423f377ade95936f6fe009b1c137bfdb \
                    file://node_modules/log4js/LICENSE;md5=8a7df09be5dd1002c10a82f81acd84aa \
                    file://node_modules/ms/license.md;md5=fd56fd5f1860961dfa92d313167c37a6 \
                    file://node_modules/rfdc/LICENSE;md5=fc2ea1f4c58a804909742c8eadede5ea \
                    file://node_modules/streamroller/LICENSE;md5=11e69b4aa865a34554aefa2958c837cf \
                    file://node_modules/universalify/LICENSE;md5=a734c6ad6e37a515025ac5e8e90ef786"

SRC_URI = " \
    npm://registry.npmjs.org/;package=ilib-lint-webos;version=${PV} \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
    file://ilib-lint-config.json \
"

S = "${WORKDIR}/npm"

PR = "r0"

inherit npm
inherit native

do_install() {
    install -d ${D}${base_prefix}/opt/i18nlint-tool/node_modules/${BPN}
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt/i18nlint-tool/node_modules/${BPN}
    # ilib-lint-config.json
    cp --no-dereference --preserve=mode,links -v ${UNPACKDIR}/ilib-lint-config.json ${D}${base_prefix}/opt/i18nlint-tool/
}

SYSROOT_DIRS += "${base_prefix}/opt"

LICENSE:${PN} = "Apache-2.0"
LICENSE:${PN}-date-format = "MIT"
LICENSE:${PN}-debug = "MIT"
LICENSE:${PN}-flatted = "ISC"
LICENSE:${PN}-fs-extra = "MIT"
LICENSE:${PN}-graceful-fs = "ISC"
LICENSE:${PN}-ilib-lint-common = "Apache-2.0"
LICENSE:${PN}-jsonfile = "MIT"
LICENSE:${PN}-log4js = "Apache-2.0"
LICENSE:${PN}-ms = "MIT"
LICENSE:${PN}-rfdc = "MIT"
LICENSE:${PN}-streamroller = "MIT"
LICENSE:${PN}-universalify = "MIT"
