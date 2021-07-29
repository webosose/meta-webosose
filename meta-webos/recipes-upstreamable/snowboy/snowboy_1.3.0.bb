SUMMARY = "Snowboy Hotword Detection by KITT.AI"
HOMEPAGE = "https://snowboy.kitt.ai/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8d385adc93db3f7e5d092b82fb2c9353"

SRCREV = "052db3400c5f45d8ac2bbf5688ad92f37b81e2ec"
SRC_URI = "git://github.com/Kitt-AI/snowboy;protocol=https;branch=master"
S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(machine-native|android)"

INSANE_SKIP:${PN} += "already-stripped"

def get_snowboy_arch(d):
    machine = d.getVar('MACHINE')
    if machine == "androidarm":
        return "android/armv7a"
    elif machine == "androidarm64":
        return "android/armv8-aarch64"
    else:
        return "ubuntu64"

SNOWBOY_ARCH ?= "${@get_snowboy_arch(d)}"

do_install() {
    mkdir -p ${D}${libdir}
    cp ${S}/lib/${SNOWBOY_ARCH}/libsnowboy-detect.a ${D}${libdir}
    mkdir -p ${D}${includedir}
    cp ${S}/include/snowboy-detect.h ${D}${includedir}
}
