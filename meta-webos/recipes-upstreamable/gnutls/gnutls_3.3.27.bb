require recipes-support/gnutls/gnutls.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://COPYING.LESSER;md5=a6f89e2100d9b6cdffcea4f398e37343"

FILESEXTRAPATHS_prepend = "${COREBASE}/meta/recipes-support/${BPN}/${BPN}:"

MIRRORS += "ftp://ftp.gnutls.org/gcrypt/gnutls ${GNUPG_MIRROR}/gnutls \n"

SRC_URI += " \
    file://correct_rpl_gettimeofday_signature.patch \
    file://configure.ac-fix-sed-command.patch \
    file://use-pkg-config-to-locate-zlib.patch \
"
SRC_URI[md5sum] = "8ee8cebd7f7575b11f232766a21c31d3"
SRC_URI[sha256sum] = "8dfda16c158ef5c134010d51d1a91d02aa5d43b8cb711b1572650a7ffb56b17f"

# This version doesn't support this option added in newer gnutls
# ERROR: gnutls-3.3.27-r0 do_configure: QA Issue: gnutls: configure was passed unrecognised options: --with-idn [unknown-configure-option]
PACKAGECONFIG[libidn] = ""
# but it has the dependency
DEPENDS += "libidn"

# This version doesn't support this option added in newer gnutls
EXTRA_OECONF_remove = "--without-libunistring-prefix"
