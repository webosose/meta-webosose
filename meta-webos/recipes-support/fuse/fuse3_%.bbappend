# Copyright (c) 2020-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos3"

VIRTUAL-RUNTIME_bash ?= "bash"
# ERROR: fuse3-3.9.2-r0 do_package_qa: QA Issue: fuse3-ptest rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI:append = " \
    file://0001-support-fuse_passthrough-to-lib_fuse.patch \
"

inherit webos_test_provider

# Enable examples
EXTRA_OEMESON =+ " -Dexamples=true"

# Install example executables to ${webos_testsdir}/fuse3/
do_install:append() {
    install -d ${D}${webos_testsdir}/fuse3/
    example_executables=`find ${B}/example -type f -executable`
    for e in $example_executables; do
        cp -rf $e ${D}${webos_testsdir}/fuse3/
    done
}
