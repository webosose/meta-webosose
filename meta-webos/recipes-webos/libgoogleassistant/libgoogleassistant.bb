# Copyright (c) 2018-2020 LG Electronics, Inc.

SUMMARY = "Google assistant engine library"
AUTHOR = "Kyungjik Min <dp.min@lge.com>"
SECTION = "webos/library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "glib-2.0 googleapis grpc json-c pmloglib pulseaudio patchelf-native"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_class-target = "${VIRTUAL-RUNTIME_bash}"

WEBOS_VERSION = "1.0.0-5_62d5e78e4368a079baacb7c1cbb9eca84328dd43"
PR = "r2"

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

do_configure_prepend() {
    # there is this silly SONAME in prebuilt libtatlas.so:
    # objdump -x image/usr/lib/libtatlas.so | grep SONAME
    #   SONAME               /usr/local/atlas/lib/libtatlas.so
    # which causes image/usr/lib/libgoogleassistant.so to need the same
    # silly path:
    # objdump -x image/usr/lib/libgoogleassistant.so | grep NEEDED.*tatlas
    #   NEEDED               /usr/local/atlas/lib/libtatlas.so
    # reset the SONAME before building libgoogleassistant.so
    patchelf --set-soname libtatlas.so ${S}/lib/libtatlas.so
}

do_install_append() {
    install -d ${D}${sysconfdir}/googleAssistant
    cp -r ${S}/tools/*.json ${D}${sysconfdir}/googleAssistant/
    cp -r ${S}/tools/*.sh ${D}${sysconfdir}/googleAssistant/
}

# ERROR: libgoogleassistant-1.0.0-4-r1 do_package_qa: QA Issue: ELF binary '/jenkins/mjansa/build-webos-thud/BUILD/work/raspberrypi3-webos-linux-gnueabi/libgoogleassistant/1.0.0-4-r1/packages-split/libgoogleassistant/usr/lib/libgoogleassistant.so.1.0.0' has relocations in .text [textrel]
# ERROR: libgoogleassistant-1.0.0-4-r1 do_package_qa: QA Issue: No GNU_HASH in the ELF binary /jenkins/mjansa/build-webos-thud/BUILD/work/raspberrypi3-webos-linux-gnueabi/libgoogleassistant/1.0.0-4-r1/packages-split/libgoogleassistant-dev/usr/lib/libtatlas.so, didn't pass LDFLAGS? [ldflags]
INSANE_SKIP_${PN} = "ldflags textrel"

# ERROR: libgoogleassistant-1.0.0-4-r1 do_package_qa: QA Issue: -dev package contains non-symlink .so: libgoogleassistant-dev path '/work/raspberrypi3-webos-linux-gnueabi/libgoogleassistant/1.0.0-4-r1/packages-split/libgoogleassistant-dev/usr/lib/libtatlas.so' [dev-elf]
# /usr/lib/libtatlas.so is really elf library, but there is also
# /usr/lib/libgoogleassistant.so -> libgoogleassistant.so.1
# which we need to explicitly add to PN-dev after
FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/libtatlas.so"
FILES_${PN}-dev += "${libdir}/libgoogleassistant.so"

# From http://gpro.lge.com/254812
SRC_URI += "file://0001-CMakeLists.txt-install-libtatlas.so-in-regular-libdi.patch"

PNBLACKLIST[libgoogleassistant] ?= "Needs to be updated for new protobuf"
