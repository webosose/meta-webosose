# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosupdater1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " file://0001-Speed-up-mount-when-rather-than-just-wait.patch"
