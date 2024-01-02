# Copyright (c) 2019-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# We need to revert RDEPENDS changes from
#
# commit 6048f6787b0b6e98f8d16710ffb68fe10c41e0a2
# Author: Changqing Li <changqing.li@windriver.com>
# Date:   Tue Apr 23 17:43:49 2019 +0800
# Subject: m4: add ptest support
#
# because as described in:
# http://lists.openembedded.org/pipermail/openembedded-commits/2017-November/215520.html
# http://lists.openembedded.org/pipermail/openembedded-core/2018-October/156694.html
# it doesn't work when GLIBC_GENERATE_LOCALES are restricted like they are in our builds:
# meta-webos/conf/distro/include/webos-toolchain.inc:GLIBC_GENERATE_LOCALES = "en_US.UTF-8"

RDEPENDS:${PN}-ptest:remove:libc-glibc = " \
    locale-base-fr-fr.iso-8859-1 \
"
