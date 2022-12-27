# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack', '', d)}"
