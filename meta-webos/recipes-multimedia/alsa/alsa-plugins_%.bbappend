# Copyright (c) 2015-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

# We don't have speexdsp in our layers
PACKAGECONFIG:remove = "speexdsp"

inherit webos_machine_impl_dep

FILESEXTRAPATHS:prepend:emulator := "${THISDIR}/${BPN}:"
SRC_URI:append:emulator = " file://99-pulseaudio-qemu-emulator.conf"

do_install:append:emulator() {
    install -v -m 644 ${UNPACKDIR}/99-pulseaudio-qemu-emulator.conf ${D}${datadir}/alsa/alsa.conf.d/99-pulseaudio-default.conf
}
