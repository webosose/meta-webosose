# Copyright (c) 2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosvirt2"

# ERROR: docker-ce-18.09.0-ce+git6e632f7fc395d15bce46f426086e91c01598cf59-r0 do_package_qa: QA Issue: docker-ce-contrib rdepends on bash, but it isnt a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-contrib_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}-contrib_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# ERROR: QA Issue: ELF binary 'TOPDIR/BUILD/work/raspberrypi4_64-webos-linux/docker-ce/18.09.0-ce+git6e632f7fc395d15bce46f426086e91c01598cf59-r0/packages-split/docker-ce/usr/bin/docker-proxy' has relocations in .text [textrel]
# http://caprica.lgsvl.com:8080/Errors/Details/1942830
INSANE_SKIP_${PN} += "textrel"
