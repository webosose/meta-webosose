# backported from;
# b322e1b u-boot: Upgrade to 2017.07 release
# with small do_compile_prepend added to prevent autodetecting native swig
# which was causing libfdt to be built, which in turn fails to find correct
# Python.h while cross compiling, Yocto 2.3 and newer don't have this issue
# thanks to RSS and HOSTTOOLS filtering

HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
PE = "1"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "d85ca029f257b53a96da6c2fb421e78a003a9943"

SRC_URI = "git://git.denx.de/u-boot.git \
    file://MPC8315ERDB-enable-DHCP.patch \
    file://10m50-update-device-tree.patch \
"

S = "${WORKDIR}/git"

do_compile_prepend () {
    sed 's@\(^always += $(if $(shell which swig 2> /dev/null),_libfdt.so)$\)@# do not autodetect swig, there is no swig-native dependency \1@g' -i ${S}/tools/Makefile
}
