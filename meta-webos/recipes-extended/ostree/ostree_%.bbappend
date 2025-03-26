# Copyright (c) 2019-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos3"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-dracut:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-dracut:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-mkinitcpio:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-mkinitcpio:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

VIRTUAL-RUNTIME_tar ?= "tar"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_tar}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_TAR', 'busybox', 'tar', '', d)}"

# Fails to build with gold:
# http://gecko.lge.com:8000/Errors/Details/588441
# ./.libs/libostree-1.so: error: undefined reference to 'gpg_strerror_r'
# Fixed in nanbield with https://lists.openembedded.org/g/openembedded-devel/message/103253
LDFLAGS:append = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)}"
