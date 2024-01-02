# Copyright (c) 2023-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# https://lists.openembedded.org/g/openembedded-core/message/176493
# Script for converting inetd.conf files into xinetd.conf files
PACKAGES =+ "${PN}-xconv"
FILES:${PN}-xconv = "${bindir}/xconv.pl"
RDEPENDS:${PN}-xconv += "perl"
RDEPENDS:${PN}:remove = "perl"
