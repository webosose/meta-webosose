LICENSE = "GFDL-1.3 & BSD & ( GPL-3.0 & The-Qt-Company-GPL-Exception-1.0 | The-Qt-Company-Commercial ) & ( GPL-2.0+ | LGPL-3.0 | The-Qt-Company-Commercial )"
LIC_FILES_CHKSUM = " \
    file://LICENSE.LGPL3;md5=e6a600fd5e1d9cbde2d983680233ad02 \
    file://LICENSE.GPL2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://LICENSE.GPL3;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSE.GPL3-EXCEPT;md5=763d8c535a234d9a3fb682c7ecb6c073 \
    file://LICENSE.FDL;md5=6d9f2a9af4c8b8c3c769f6cc1b6aaf7e \
"

inherit qt6-qmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

DEPENDS += "qtdeclarative"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'alsa', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'pulseaudio', 'pulseaudio', '', d)}"
PACKAGECONFIG[alsa] = "-alsa,-no-alsa,alsa-lib"
PACKAGECONFIG[pulseaudio] = "-pulseaudio,-no-pulseaudio,pulseaudio"
#PACKAGECONFIG[openal] = "-feature-openal,-no-feature-openal,openal-soft"
PACKAGECONFIG[gstreamer] = "-gstreamer 1.0,,gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"
PACKAGECONFIG[gstreamer010] = "-gstreamer 0.10,,gstreamer gst-plugins-base gst-plugins-bad"

# ERROR: Unknown command line option '-DQT_BUILD_EXAMPLES=OFF'.
PACKAGECONFIG[tests] = ","
PACKAGECONFIG[examples] = ","

EXTRA_QMAKEVARS_CONFIGURE += "${PACKAGECONFIG_CONFARGS}"

# Disable GStreamer if completely disabled
EXTRA_QMAKEVARS_CONFIGURE += "${@bb.utils.contains_any('PACKAGECONFIG', 'gstreamer gstreamer010', '', '-no-gstreamer', d)}"

CXXFLAGS += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', '-DMESA_EGL_NO_X11_HEADERS=1', d)}"

# The same issue as in qtbase:
# http://errors.yoctoproject.org/Errors/Build/44914/
LDFLAGS_append_x86 = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)}"

QT_MODULE_BRANCH = "dev"
# Needs the "_${QT_MODULE}" suffix (it's not an override),
# since v6.2.0-rc2 with https://code.qt.io/cgit/yocto/meta-qt6.git/commit/?id=f61b87ae78ff376c206baad10bc4184328889db7
SRCREV_qtmultimedia = "e22a4c82ee24d3d574a6be629e3049248cfba9d9"
PV = "6.0.0"

SRC_URI += "\
    file://0001-Fix-build-error-for-qtmultimedia-dev-branch.patch \
"
