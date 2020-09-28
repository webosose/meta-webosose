# Copyright (c) 2012-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos11"

# Enable c-ares for DNS lookup
PACKAGECONFIG[c-ares] = "--enable-ares,--disable-ares,c-ares"
PACKAGECONFIG_append_class-target = " c-ares"
# Added in Yocto 2.4, but conflicts with c-ares implementation
PACKAGECONFIG_remove_class-target = " threaded-resolver"

# Disable GnuTLS, enable OpenSSL
PACKAGECONFIG_remove = "gnutls"
# NB. It doesn't appear to be necessary to give --with-ssl a =PATH value; what
# configure says is the default value (/usr/local/ssl) doesn't appear anywhere
# in the build or temp directories when PATH is not specified.
PACKAGECONFIG_append = " ssl"

PACKAGECONFIG_append = " rtsp"

PACKAGECONFIG_append = " dict gopher imap pop3 smtp telnet tftp"

# libidn2 is LGPLv3, remove support for it
PACKAGECONFIG_remove = "libidn"
