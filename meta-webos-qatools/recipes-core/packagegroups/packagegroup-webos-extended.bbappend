# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "qatools1"

inherit webos_prerelease_dep

RDEPENDS_${PN}_append = " \
    ${@'' if '${WEBOS_DISTRO_PRERELEASE}' == '' else 'telluriumd telluriumnub qttestability'} \
"
RDEPENDS_${PN}_append_hardware = " \
    ${@'' if '${WEBOS_DISTRO_PRERELEASE}' == '' else 'event-device-creator'} \
"
