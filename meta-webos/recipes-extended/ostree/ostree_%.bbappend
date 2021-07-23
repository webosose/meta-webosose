# Copyright (c) 2019-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-dracut_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-dracut_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-mkinitcpio_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-mkinitcpio_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

VIRTUAL-RUNTIME_tar ?= "tar"
RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_tar}"
RDEPENDS_${PN}-ptest_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_TAR', 'busybox', 'tar', '', d)}"

# It should be added with:
# ${@bb.utils.contains('PACKAGECONFIG', 'trivial-httpd-cmdline', '${PN}-trivial-httpd', '', d)}
# like in ${PN} RDEPENDS, I've sent fix to meta-oe, just remove it for now (as we don't enable trivial-httpd-cmdline here)
RDEPENDS_${PN}-ptest_remove = "${PN}-trivial-httpd"
