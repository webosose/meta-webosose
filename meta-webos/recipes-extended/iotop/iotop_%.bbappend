# Copyright (c) 2015-2022 LG Electronics, Inc.

EXTENDPRAUTO = "webos2"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://iotop_not_disappear_result_when_exited_thread.patch"
