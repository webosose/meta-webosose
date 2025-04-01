# Copyright (c) 2013-2025 LG Electronics, Inc.

PKGV .= "-0webos4"
EXTENDPRAUTO:append = "webos4"

# We need to revert changes from
# http://lists.openembedded.org/pipermail/openembedded-core/2018-October/156510.html
# because as described in:
# http://lists.openembedded.org/pipermail/openembedded-commits/2017-November/215520.html
# http://lists.openembedded.org/pipermail/openembedded-core/2018-October/156694.html
# it doesn't work when GLIBC_GENERATE_LOCALES are restricted like they are in our builds:
# meta-webos/conf/distro/include/webos-toolchain.inc:GLIBC_GENERATE_LOCALES = "en_US.UTF-8"
RDEPENDS:${PN}-ptest:remove:libc-glibc = " \
    locale-base-pl-pl.iso-8859-2 \
    locale-base-tr-tr \
    locale-base-lt-lt \
    locale-base-ja-jp.euc-jp \
    locale-base-fa-ir \
    locale-base-ru-ru \
    locale-base-de-de \
    locale-base-pl-pl \
    locale-base-hr-hr \
    locale-base-el-gr \
    locale-base-fr-fr \
    locale-base-fr-fr \
    locale-base-es-es \
    locale-base-en-gb \
"
