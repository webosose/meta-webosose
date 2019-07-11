# Copyright (c) 2015-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos7"

# Assign to PE, because empty PKGE in KERNEL_IMAGE_NAME causes two hyphens.
PE = "1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_qemux86 = " file://crypto.cfg \
                           file://my_gfx.cfg \
                           file://vbox_gfx.cfg \
                           file://sound.cfg \
                           file://enable_uinput.cfg \
                           file://network_testing.cfg \
                           file://0000-change-keymax-value.patch \
                           file://0002-virt-vbox-Mark-expected-switch-fall-through.patch \
                           file://0003-virt-vbox-Implement-passing-requestor-info-to-the-ho.patch \
                           file://file_system.cfg \
"

KERNEL_MODULE_AUTOLOAD_append_qemux86 = "\
        vboxguest \
"
KERNEL_MODULE_AUTOLOAD_append_qemux86-64 = "\
        vboxguest \
"
