# Copyright (c) 2012-2024 LG Electronics, Inc.

PKGV .= "-0webos3"
EXTENDPRAUTO:append = "webos15"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://0001-Add-tzset-applet.patch \
    file://0002-date-add-support-for-options-U-and-S.patch \
    file://0003-libedit-check-for-null-before-passing-cmdedit_prompt.patch \
"

RPROVIDES:${PN} += "stat"
RPROVIDES:${PN} += "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
RPROVIDES:${PN} += "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_TAR', 'busybox', 'tar', '', d)}"

# Needed with usrmerge when the bash symlink is installd in /usr/bin/bash
# but there is also /bin -> /usr/bin symlink which allows to use /bin/bash
# in shebangs, but file-rdeps doesn't know about it and fail
# Regular bash recipe has the same since:
# https://git.openembedded.org/openembedded-core/commit/?h=kirkstone&id=4759408677a4e60c5fa7131afcb5bc184cf2f90a
RPROVIDES:${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', '/bin/bash /bin/echo', '', d)}"
