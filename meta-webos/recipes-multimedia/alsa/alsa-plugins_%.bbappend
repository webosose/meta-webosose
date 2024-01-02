# Copyright (c) 2015-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

# We don't have speexdsp in our layers
PACKAGECONFIG:remove = "speexdsp"

inherit webos_machine_impl_dep

FILESEXTRAPATHS:prepend:emulator := "${THISDIR}/${BPN}:"
SRC_URI:append:emulator = " file://99-pulseaudio-qemu-emulator.conf"

do_install:append:emulator() {
    install -v -m 644 ${WORKDIR}/99-pulseaudio-qemu-emulator.conf ${D}${datadir}/alsa/alsa.conf.d/99-pulseaudio-default.conf
}

FILESEXTRAPATHS:prepend:qemux86-64 := "${THISDIR}/${BPN}:"
SRC_URI:append:qemux86-64 = " file://99-pulseaudio-qemu-emulator.conf"

do_install:append:qemux86-64() {
    install -v -m 644 ${WORKDIR}/99-pulseaudio-qemu-emulator.conf ${D}${datadir}/alsa/alsa.conf.d/99-pulseaudio-default.conf
}
