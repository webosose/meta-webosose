# Copyright (c) 2020-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosvirt2"

# ERROR: docker-ce-18.09.0-ce+git6e632f7fc395d15bce46f426086e91c01598cf59-r0 do_package_qa: QA Issue: docker-ce-contrib rdepends on bash, but it isnt a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-contrib:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-contrib:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# We don't have seccomp in DISTRO_FEATURES
PACKAGECONFIG:remove = "seccomp"

# ERROR: QA Issue: ELF binary 'TOPDIR/BUILD/work/raspberrypi4_64-webos-linux/docker-ce/18.09.0-ce+git6e632f7fc395d15bce46f426086e91c01598cf59-r0/packages-split/docker-ce/usr/bin/docker-proxy' has relocations in .text [textrel]
# http://caprica.lgsvl.com:8080/Errors/Details/1942830
INSANE_SKIP:${PN} += "textrel"
