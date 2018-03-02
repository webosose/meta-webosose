# Copyright (c) 2013-2015 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

SRC_URI += " file://CVE-2007-4476.diff \
             file://CVE-2007-4131.diff \
"

# This version doesn't have the options for posix-acl
PACKAGECONFIG[acl] = ""
