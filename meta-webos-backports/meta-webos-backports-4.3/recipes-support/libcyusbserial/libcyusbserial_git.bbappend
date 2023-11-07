# Backport changes from
# https://git.openembedded.org/meta-openembedded/commit/?id=e98e9141f89628549f1b11527f94a437d2c46466
# https://lists.openembedded.org/g/openembedded-devel/message/104698
# https://lists.openembedded.org/g/openembedded-devel/message/104724

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-CMakeLists.txt-don-t-fall-back-CMAKE_INSTALL_LIBDIR-.patch"
