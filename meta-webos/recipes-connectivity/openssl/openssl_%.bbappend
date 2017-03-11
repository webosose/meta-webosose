# Copyright (c) 2013-2017 LG Electronics, Inc.

PKGV .= "0webos19"

EXTENDPRAUTO_append = "webos21"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

inherit update-alternatives
ALTERNATIVE_${PN} = "openssl-cnf2"
ALTERNATIVE_LINK_NAME[openssl-cnf2] = "${sysconfdir}/ssl/openssl.cnf"
ALTERNATIVE_PRIORITY[openssl-cnf2] ?= "1"

# Using version script changes ABI and our prebuilt binaries aren't compatible with
# the new 1.0.2 ABI
SRC_URI_remove_class-target = "file://debian1.0.2/version-script.patch"
# Restore the old version of version-script from pre-1.0.2 (the versions of the symbols
# are wrong, but this is the ABI our prebuilt libraries are using).
FILESEXTRAPATHS_prepend_class-target := "${THISDIR}/${BPN}:"
SRC_URI_append_class-target = " file://version-script.patch"

do_install_append() {
    mv ${D}${sysconfdir}/ssl/openssl.cnf ${D}${sysconfdir}/ssl/openssl.cnf.${BPN}
}

pkg_postinst_openssl-conf_forcevariable () {
    :
}
