# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'attr smack', '', d)}"

