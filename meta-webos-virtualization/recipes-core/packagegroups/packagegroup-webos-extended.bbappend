# Copyright (c) 2019-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosvirt3"

RDEPENDS:${PN}:append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'docker-moby', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'docker-simple-webserver', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'docker-compose', '', d)} \
"
