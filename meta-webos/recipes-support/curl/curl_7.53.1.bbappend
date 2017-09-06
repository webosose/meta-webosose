# Copyright (c) 2012-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos6"

# Enable c-ares for DNS lookup
PACKAGECONFIG[c-ares] = "--enable-ares,--disable-ares,c-ares"
PACKAGECONFIG_append_class-target = " c-ares"
# Added in Yocto 2.4, but conflicts with c-ares implementation
PACKAGECONFIG_remove_class-target = " threaded-resolver"

# Force to use libcurl5 instead of libcurl4 (like we had with Yocto 1.7 Dizzy)
# The SONAME isn't bumped automatically since oe-core commit 49c848018484827c433e1bcf9c63416640456f3e
# which changed SIZEOF_OFF_T to 8
EXTRA_OECONF += "--enable-soname-bump"

# Disable GnuTLS, enable OpenSSL
PACKAGECONFIG_remove = "gnutls"
# NB. It doesn't appear to be necessary to give --with-ssl a =PATH value; what
# configure says is the default value (/usr/local/ssl) doesn't appear anywhere
# in the build or temp directories when PATH is not specified.
PACKAGECONFIG_append = " ssl"

PACKAGECONFIG_append = " rtsp"
