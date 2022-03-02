# Copyright (c) 2022 LG Electronics, Inc.

SUMMARY = "Enhanced eBPF Debugger"
AUTHOR = "Kim Jaehyun <jehn.kim@lge.com>"
SECTION = "console/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=e3fc50a88d0a364313df4b21ef20c29e"

PR = "r0"

inherit webos_public_repo
inherit webos_enhanced_submissions

WEBOS_REPO_NAME = "ebd-core"
WEBOS_GIT_PARAM_BRANCH = "webos-5.4"

SRC_URI = " \
    ${WEBOS_KERNEL_GIT_REPO_COMPLETE} \
    git://github.com/libbpf/libbpf.git;protocol=http;destsuffix=git/src/cc/libbpf;name=libbpf \
    file://remove_btf_dependency.patch \
"

WEBOS_VERSION = "1.0.0-3_00e6dcdd1ec324baf75f503fe3906cb8e37c59f1"

SRCREV_libbpf = "26e768783ce99bd16540a5064cee8677818d2358"

S = "${WORKDIR}/git"

DEPENDS = "zlib elfutils"

do_compile() {
    cd ${S}/libbpf-tools
    oe_runmake -f Makefile.ebd ARCH=${TARGET_ARCH} LDFLAGS=-static OUTPUT=${WORKDIR}/build
}

do_install() {
    install -d ${D}${bindir}
    install -v -m 0755 ${WORKDIR}/build/bin/* ${D}${bindir}
}

FILES_${PN} = "${bindir}/ebd"
