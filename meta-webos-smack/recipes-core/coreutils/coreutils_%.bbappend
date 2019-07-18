# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack', '', d)}"
