# Copyright (c) 2015-2019 LG Electronics, Inc.

EXTENDPRAUTO = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://iotop_not_disappear_result_when_exited_thread.patch"
