# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

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

# It should be added with:
# ${@bb.utils.contains('PACKAGECONFIG', 'trivial-httpd-cmdline', '${PN}-trivial-httpd', '', d)}
# like in ${PN} RDEPENDS, I've sent fix to meta-oe, just remove it for now (as we don't enable trivial-httpd-cmdline here)
RDEPENDS:${PN}-ptest:remove = "${PN}-trivial-httpd"
