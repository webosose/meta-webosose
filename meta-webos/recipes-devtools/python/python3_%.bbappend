# Copyright (c) 2019-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://CVE-2021-3177.patch \
"

# We need to revert changes from
#
# commit 49720e6d680d0041850c00ce6dc859d557825595
# Author: Alexander Kanavin <alex.kanavin@gmail.com>
# Date:   Thu Apr 11 18:00:37 2019 +0200
# Subject: python3: add a tr-tr locale for test_locale ptest
#
# because as described in:
# http://lists.openembedded.org/pipermail/openembedded-commits/2017-November/215520.html
# http://lists.openembedded.org/pipermail/openembedded-core/2018-October/156694.html
# it doesn't work when GLIBC_GENERATE_LOCALES are restricted like they are in our builds:
# meta-webos/conf/distro/include/webos-toolchain.inc:GLIBC_GENERATE_LOCALES = "en_US.UTF-8"

RDEPENDS_${PN}-ptest_remove_libc-glibc = " \
    locale-base-tr-tr.iso-8859-9 \
"

# ERROR: python3-3.8.1-r0 do_package_qa: QA Issue: /usr/lib/python3.8/test/ziptestdata/header.sh contained in package python3-tests requires /bin/bash, but no providers found in RDEPENDS_python3-tests? [file-rdeps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-tests_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-tests_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
