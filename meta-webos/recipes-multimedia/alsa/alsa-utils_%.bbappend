# Copyright (c) 2014-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos3"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# this patch adds --disable-libsamplerate configure option
SRC_URI += "file://disable-libsamplerate.patch"

DEPENDS:remove = "libsamplerate0"

PACKAGECONFIG = ""
PACKAGECONFIG[libsamplerate] = "--enable-libsamplerate,--disable-libsamplerate,libsamplerate0"

# We have disabled topology support in our alsa-lib bbappend, so this package
# won't be built by alsa-utils and we need to remove runtime dependency on it
RDEPENDS:${PN}:remove = "alsa-utils-alsatplg"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-scripts:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-scripts:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
