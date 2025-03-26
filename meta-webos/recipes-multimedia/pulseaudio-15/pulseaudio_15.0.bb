# Imported from oe-core as-is in kirkstone:
# 1800c06e25 pulseaudio: conditionally depend on alsa-plugins-pulseaudio-conf
# to avoid changes from pulseaudio-16 in:
# https://git.openembedded.org/openembedded-core/commit/?id=e5399a09bf44700f97607b283379172dac0cf9c7

require pulseaudio.inc

SRC_URI = "http://freedesktop.org/software/pulseaudio/releases/${BP}.tar.xz \
           file://0001-client-conf-Add-allow-autospawn-for-root.patch \
           file://0002-do-not-display-CLFAGS-to-improve-reproducibility-bui.patch \
           file://0001-meson-Check-for-__get_cpuid.patch \
           file://volatiles.04_pulse \
           file://0001-doxygen-meson.build-remove-dependency-on-doxygen-bin.patch \
           "
SRC_URI[sha256sum] = "a40b887a3ba98cc26976eb11bdb6613988f145b19024d1b6555c6a03c9cba1a0"
UPSTREAM_CHECK_REGEX = "pulseaudio-(?P<pver>\d+(\.(?!99)\d+)+)\.tar"
