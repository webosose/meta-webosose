# Copyright (c) 2019-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack1', '', d)}"

RDEPENDS:${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'attr smack com.webos.app.test.smack.native', '', d)}"
