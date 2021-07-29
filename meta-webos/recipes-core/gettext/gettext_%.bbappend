# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# We need to revert RDEPENDS changes from
#
# commit b3147117a32e2bfe851ffa00951dfb28af4ab7e6
# Author: Alexander Kanavin <alex.kanavin@gmail.com>
# Date:   Tue Dec 17 17:00:11 2019 +0100
# Subject: gettext: fix failing ptests
#
# because as described in:
# http://lists.openembedded.org/pipermail/openembedded-commits/2017-November/215520.html
# http://lists.openembedded.org/pipermail/openembedded-core/2018-October/156694.html
# it doesn't work when GLIBC_GENERATE_LOCALES are restricted like they are in our builds:
# meta-webos/conf/distro/include/webos-toolchain.inc:GLIBC_GENERATE_LOCALES = "en_US.UTF-8"

RDEPENDS:${PN}-ptest:remove:libc-glibc = "\
    locale-base-de-de \
    locale-base-fr-fr \
"

RRECOMMENDS:${PN}-ptest:remove:libc-glibc = "\
    locale-base-de-de.iso-8859-1 \
    locale-base-fr-fr.iso-8859-1 \
"
