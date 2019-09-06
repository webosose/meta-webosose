# Copyright (c) 2017-2019 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi4"

PROVIDES_remove_rpi = "\
    virtual/libgles2 \
    virtual/egl \
"

RPROVIDES_${PN}_remove_rpi ="\
    libgles2 \
    libgl \
    egl \
    libegl \
"

FILESEXTRAPATHS_prepend_rpi := "${THISDIR}/${BPN}:"
SRC_URI_append_rpi = " \
    file://0001-openmaxil-add-pkg-config-file.patch \
    file://0002-Remove-EGL-dependency.patch \
"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target_rpi = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_class-target_rpi = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

PROVIDES_append_rpi = " virtual/libomxil"

# Backport of the commit 60dbfd2 from meta-raspberrypi(warrior)
do_install_append () {
    install -D -m 0755 ${D}${prefix}${sysconfdir}/init.d/vcfiled ${D}${sysconfdir}/init.d/vcfiled
    rm -rf ${D}${prefix}${sysconfdir}
}

# Backport of the commit fe7725e from meta-raspberrypi(warrior)
SRCREV = "517cdc30da167d81a485e7a994e02cec2390a269"
PV = "20190501"
