# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosupdater1"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " file://0001-Speed-up-mount-when-rather-than-just-wait.patch"
