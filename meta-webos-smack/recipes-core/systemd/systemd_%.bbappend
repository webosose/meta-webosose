# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'smack4', '', d)}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', '\
    file://0001-SMACK-add-loading-unconfined-label.patch \
    file://0001-meson-add-smack-default-process-label-option.patch \
    file://0001-fileio.c-fix-build-with-smack-enabled.patch \
', '', d)}"

SYSTEMD_SMACK_RUN_LABEL = "System"
SYSTEMD_SMACK_DEFAULT_PROCESS_LABEL = "System::Run"

EXTRA_OEMESON += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', '\
    -Dsmack-run-label=${SYSTEMD_SMACK_RUN_LABEL} \
    -Dsmack-default-process-label=${SYSTEMD_SMACK_DEFAULT_PROCESS_LABEL} \
', '', d)}"

do_install[postfuncs] += "${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'set_tmp_star', '', d)}"

set_tmp_star () {
    tmpmount="${D}/${systemd_unitdir}/system/tmp.mount"
    if [ -f "$tmpmount" ]; then
        sed -i -e 's/^Options=/Options=smackfsroot=*,/' "$tmpmount"
    fi
}
