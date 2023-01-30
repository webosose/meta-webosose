# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# https://lists.openembedded.org/g/openembedded-devel/message/100861
# ispellaff2myspell: A program to convert ispell affix tables to myspell format
PACKAGES =+ "${PN}-ispell"
FILES:${PN}-ispell = "${bindir}/ispellaff2myspell"
RDEPENDS:${PN}-ispell = "perl"
RDEPENDS:${PN}:remove = "perl"
