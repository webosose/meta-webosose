# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosupdater1"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-dracut_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-dracut_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
