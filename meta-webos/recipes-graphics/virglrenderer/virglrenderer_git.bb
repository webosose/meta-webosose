SUMMARY = "VirGL virtual OpenGL renderer"
HOMEPAGE = "https://virgil3d.github.io/"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=c81c08eeefd9418fca8f88309a76db10"

DEPENDS = "libdrm mesa libepoxy"

PV = "0.6.0+git${SRCPV}"
SRCREV = "7f30999e060504b5be6851843858f814d11667b2"
SRC_URI = "git://anongit.freedesktop.org/virglrenderer \
    file://link.with.libdrm.patch \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

BBCLASSEXTEND = "native nativesdk"
