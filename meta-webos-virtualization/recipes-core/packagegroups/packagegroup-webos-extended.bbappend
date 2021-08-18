# Copyright (c) 2019-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosvirt2"

RDEPENDS_${PN}_append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'docker-moby', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'docker-simple-webserver', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'python3-docker-compose', '', d)} \
"
