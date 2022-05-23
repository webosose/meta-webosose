# Copyright (c) 2012-2017 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

# All the original recipe does is stage a tarball and some autotools files;
# nothing compiled.
inherit allarch

# These files aren't very usefull on target image, package them all in PN-dev
FILES:${PN} = ""

FILES:${PN}-dev += " \
    ${datadir}/${BPN}/build \
    ${datadir}/${BPN}/doctags \
    ${datadir}/${BPN}/doctool \
    ${bindir}/mm-common-get \
    ${bindir}/mm-common-prepare \
"
# ${PN} package is empty, remove the default dependency on it
RDEPENDS:${PN}-dev = ""
