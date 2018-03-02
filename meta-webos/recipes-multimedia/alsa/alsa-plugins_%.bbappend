# Copyright (c) 2015-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# We don't have speexdsp in our layers
PACKAGECONFIG_remove = "speexdsp"

inherit webos_machine_impl_dep

FILESEXTRAPATHS_prepend_emulator := "${THISDIR}/${BPN}:"
SRC_URI_append_emulator = " file://99-pulseaudio-qemu-emulator.conf"

do_install_append_emulator() {
    install -v -m 644 ${WORKDIR}/99-pulseaudio-qemu-emulator.conf ${D}${datadir}/alsa/alsa.conf.d/99-pulseaudio-default.conf
}
