SUMMARY = "AudioProcessing module from the WebRTC project"
DESCRIPTION = "Audio processing bits of the WebRTC reference implementation"
HOMEPAGE = "https://www.freedesktop.org/software/pulseaudio/webrtc-audio-processing/"
SECTION = "audio"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=da08a38a32a340c5d91e13ee86a118f2"

# This was added based on 0.3.1 version from meta-oe, for pulseaudio-module-agc
# This version is difficult to be upgraded in meta-oe as upstream pulseaudio
# module-echo-cancel doesn't build with newer webrtc-audio-processing (it's not
# enabled by default) and it's also used as optional dependency from oe-core's
# gstreamer1.0-plugins-bad and dependency from pipewire in meta-oe where it might
# be incompatible as well.

DEPENDS:append:libc-musl = " libexecinfo"
DEPENDS = "abseil-cpp"

SRC_URI = "https://www.freedesktop.org/software/pulseaudio/${BPN}/${BP}.tar.gz \
    file://0001-Fix-return-type-errors.patch \
    file://0002-build-Use-cmake-to-look-up-abseil-dependency.patch \
    file://0003-build-Add-library-based-absl-detection-as-a-fallback.patch \
    file://0004-Use-pkg-config-for-abseil-cpp-detection-if-available.patch \
    file://0005-Add-missing-absl-library-for-bad_optional_access.patch \
    file://0006-Add-an-abseil-subproject-and-correctly-specify-fallb.patch \
    file://0007-file_utils.h-Fix-build-with-gcc-13.patch \
"

SRC_URI[sha256sum] = "441a30d2717b2eb4145c6eb96c2d5a270fe0b4bc71aebf76716750c47be1936f"

PR = "r1"

LDFLAGS:append:libc-musl = " -lexecinfo"

inherit pkgconfig meson
