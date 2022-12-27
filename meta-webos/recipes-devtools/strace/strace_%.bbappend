# Copyright (c) 2020-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# We need to revert RDEPENDS changes from
# https://git.openembedded.org/openembedded-core/commit/?id=ea6f27e35b6cac4db9e1ab0d9a888d15daba6c09
#
# because as described in:
# http://lists.openembedded.org/pipermail/openembedded-commits/2017-November/215520.html
# http://lists.openembedded.org/pipermail/openembedded-core/2018-October/156694.html
# it doesn't work when GLIBC_GENERATE_LOCALES are restricted like they are in our builds:
# meta-webos/conf/distro/include/webos-toolchain.inc:GLIBC_GENERATE_LOCALES = "en_US.UTF-8"

RDEPENDS:${PN}-ptest:remove:libc-glibc = "\
    locale-base-en-us.iso-8859-1 \
"
