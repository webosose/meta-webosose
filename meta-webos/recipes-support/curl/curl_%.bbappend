# Copyright (c) 2012-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos11"

# Enable c-ares for DNS lookup
PACKAGECONFIG[c-ares] = "--enable-ares,--disable-ares,c-ares"
PACKAGECONFIG:append:class-target = " c-ares"
# Added in Yocto 2.4, but conflicts with c-ares implementation
PACKAGECONFIG:remove:class-target = " threaded-resolver"

# Disable GnuTLS, enable OpenSSL
PACKAGECONFIG:remove = "gnutls"
# NB. It doesn't appear to be necessary to give --with-ssl a =PATH value; what
# configure says is the default value (/usr/local/ssl) doesn't appear anywhere
# in the build or temp directories when PATH is not specified.
PACKAGECONFIG:append = " openssl"

PACKAGECONFIG:append = " rtsp"

PACKAGECONFIG:append = " dict gopher imap pop3 smtp telnet tftp"

# ERROR: curl-7.84.0-r0 do_package_qa: QA Issue: curl-ptest rdepends on bash, but it isn't a build dependency, missing bash in DEPENDS or PACKAGECONFIG? [build-deps]
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}-ptest:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}-ptest:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"
