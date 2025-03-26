# Copyright (c) 2017-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos22"
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:webos = " \
    file://0001-systemd-oomd-depend-on-swap-on.patch \
    file://0002-Add-webos-interface.patch \
    file://0003-systemd-oomd-modify-oomd.conf.patch \
    file://0004-oomd-to-some.patch \
    file://0005-oomd-change-duration.patch \
    file://0006-Change-ownership-of-media-directory-to-support-non-r.patch \
"

inherit webos_prerelease_dep

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-kernel-install:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-kernel-install:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

RDEPENDS:${PN}:append = " libdw"
RDEPENDS:${PN}:remove = "update-rc.d"

PACKAGECONFIG:remove = " \
    networkd    \
    resolved    \
    nss-resolve \
    timedated   \
    timesyncd   \
"

PACKAGECONFIG:append = " \
    ${@oe.utils.conditional('DISTRO', 'webos', 'oomd', '', d)} \
    ${@oe.utils.conditional('DISTRO', 'webos', 'cgroupv2', '', d)} \
    coredump \
    elfutils \
"

CFLAGS += "${SECURITY_STACK_PROTECTOR}"

FILES:${PN} += "${@oe.utils.conditional('DISTRO', 'webos','${datadir}/dbus-1/system.d/com.webos.MemoryManager1.conf', '', d)}"

# By default systemd's Predictable Network Interface Names policy configured for qemu
# Currently we don't support this policy in qemu, so removing from systemd's configuration
do_install:append:qemuall() {
    rm -rf ${D}/${base_libdir}/systemd/network/99-default.link
}

PR:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack4', '', d)}"
PATCH_SMACK = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', '\
    file://0001-SMACK-add-loading-unconfined-label.patch \
    file://0001-meson-add-smack-default-process-label-option.patch \
    file://0001-fileio.c-fix-build-with-smack-enabled.patch \
', '', d)}"

SRC_URI:append = " ${PATCH_SMACK}"

SYSTEMD_SMACK_RUN_LABEL = "System"
SYSTEMD_SMACK_DEFAULT_PROCESS_LABEL = "System::Run"

EXTRA_OEMESON_SMACK = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', '\
    -Dsmack-run-label=${SYSTEMD_SMACK_RUN_LABEL} \
    -Dsmack-default-process-label=${SYSTEMD_SMACK_DEFAULT_PROCESS_LABEL} \
', '', d)}"

EXTRA_OEMESON:append = " ${EXTRA_OEMESON_SMACK}"

do_install[postfuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'set_tmp_star', '', d)}"

set_tmp_star () {
    tmpmount="${D}/${systemd_system_unitdir}/tmp.mount"
    if [ -f "$tmpmount" ]; then
        sed -i -e 's/^Options=/Options=smackfsroot=*,/' "$tmpmount"
    fi
}
