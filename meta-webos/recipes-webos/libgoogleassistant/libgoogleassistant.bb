# Copyright (c) 2018-2019 LG Electronics, Inc.

SUMMARY = "Google assistant engine library"
AUTHOR = "Kyungjik Min <dp.min@lge.com>"
SECTION = "webos/library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "glib-2.0 googleapis grpc json-c pmloglib pulseaudio"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_class-target = "${VIRTUAL-RUNTIME_bash}"

WEBOS_VERSION = "1.0.0-4_72f471d5e617210b62fba191982f3fe0d95bdf1a"
PR = "r1"

inherit webos_library
inherit webos_cmake
inherit webos_machine_impl_dep
inherit webos_enhanced_submissions
inherit webos_public_repo
inherit webos_prebuilt_binaries

# git/lib/libsnowboy-detect.a git/lib/libtatlas.so are prebuilt binaries
# for hardfp arm only which in our builds is only the raspberrypi3 MACHINE
WEBOS_PREBUILT_BINARIES_FOR = "raspberrypi3"
COMPATIBLE_MACHINE_raspberrypi3-64 = "^$"

# Build for raspberrypi4
WEBOS_PREBUILT_BINARIES_FOR_append = " raspberrypi4"
COMPATIBLE_MACHINE_raspberrypi4-64 = "^$"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DGOOGLEAPIS_PATH=${STAGING_INCDIR}/google"

do_install_append() {
    install -d ${D}${sysconfdir}/googleAssistant
    cp -r ${S}/tools/*.json ${D}${sysconfdir}/googleAssistant/
    cp -r ${S}/tools/*.sh ${D}${sysconfdir}/googleAssistant/
}

FILES_${PN} += " \
    /usr/local/atlas/lib \
    ${sysconfdir} \
"

INSANE_SKIP_${PN} = "ldflags libdir textrel"
INSANE_SKIP_${PN}-dev = "dev-elf"
INSANE_SKIP_${PN}-dbg = "libdir"
