# Copyright (c) 2019-2020 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# needs newer gnutls than 3.3.30 from meta-gplv2
# meson.build:74:0: ERROR:  Invalid version of dependency, need 'gnutls' ['>= 3.4.6'] found '3.3.30'.
PACKAGECONFIG_remove = "gnutls"
# enable openssl instead, both cannot be disabled:
# meson.build:131:2: ERROR:  Problem encountered: No TLS backends enabled. Please enable at least one TLS backend
PACKAGECONFIG_append = " openssl"
