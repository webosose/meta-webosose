# Imported from meta-oe in Yocto 2.7 Warrior with these extra commits:
# 44725208af libvncserver: enable split client/server packages
# bd6803523c libvncserver: make PACKAGECONFIG flags explicit
# 12351cdd21 libvncserver: Update to latest commit post 0.9.12
# b2480dbeb6 libvncserver: fix configure argument on openssl when disabled
# 587cdd42bb libvncserver: Make PACKAGECONFIG easier to read by making the list multiline
# 5add8e838f libvncserver: Update to latest version

DESCRIPTION = "library for easy implementation of a RDP/VNC server"
HOMEPAGE = "https://libvnc.github.io"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=361b6b837cad26c6900a926b62aada5f"

PACKAGECONFIG ??= " \
    gcrypt \
    gnutls \
    jpeg \
    lzo \
    png \
    ${@bb.utils.contains('DISTRO_FEATURES','systemd','systemd','',d)} \
    sdl \
    zlib \
"
PACKAGECONFIG[gcrypt] = "-DWITH_GCRYPT=ON,-DWITH_GCRYPT=OFF,libgcrypt,libgcrypt"
PACKAGECONFIG[gnutls] = "-DWITH_GNUTLS=ON,-DWITH_GNUTLS=OFF,gnutls"
PACKAGECONFIG[jpeg] = "-DWITH_JPEG=ON,-DWITH_JPEG=OFF,jpeg"
PACKAGECONFIG[lzo] = "-DWITH_LZO=ON,-DWITH_LZO=OFF,lzo"
PACKAGECONFIG[openssl] = "-DWITH_OPENSSL=ON,-DWITH_OPENSSL=OFF,openssl"
PACKAGECONFIG[png] = "-DWITH_PNG=ON,-DWITH_PNG=OFF,libpng,libpng"
PACKAGECONFIG[systemd] = "-DWITH_SYSTEMD=ON,-DWITH_SYSTEMD=OFF,systemd"
PACKAGECONFIG[sdl] = "-DWITH_SDL=ON,-DWITH_SDL=OFF,libsdl2"
PACKAGECONFIG[zlib] = "-DWITH_ZLIB=ON,-DWITH_ZLIB=OFF,zlib"

PACKAGE_BEFORE_PN = "libvncclient"
FILES_libvncclient = "${libdir}/libvncclient.*"

inherit cmake

SRC_URI = "git://github.com/LibVNC/libvncserver"
SRCREV = "c0a23857a5c42b45b6d22ccf7218becd1fa69402"


S = "${WORKDIR}/git"
