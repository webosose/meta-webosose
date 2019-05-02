# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosvirt1"

RDEPENDS_${PN}_append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'docker', '', d)} \
"
