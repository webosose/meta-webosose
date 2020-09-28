# Copyright (c) 2012-2020 LG Electronics, Inc.

#GPL 3.0 using componenet downgrade
PREFERRED_VERSION_dosfstools = "2.11"
PREFERRED_VERSION_gettext = "0.16.1"
PREFERRED_VERSION_gmp = "4.2.1"
PREFERRED_VERSION_gnutls = "3.3.30"
PREFERRED_VERSION_readline = "5.2"
PREFERRED_VERSION_nettle = "2.7.1"
PREFERRED_VERSION_tar = "1.17"
PREFERRED_VERSION_gzip = "1.3.12"
PREFERRED_VERSION_elfutils = "0.148"
PREFERRED_VERSION_coreutils = "6.9"
PREFERRED_VERSION_gawk = "3.1.5"
PREFERRED_VERSION_gdbm = "1.8.3"

# readline-5.2 isn't compatible with gdb, use bundled version - gdb shouldn't be installed in end-user images
# http://lists.openembedded.org/pipermail/openembedded-core/2016-January/116409.html
PACKAGECONFIG_remove_pn-gdb = "readline"
