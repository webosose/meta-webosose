# Copyright (c) 2012-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos10"

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
