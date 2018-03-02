# Copyright (c) 2014 LG Electronics, Inc.

# Drop patch for CVE-2009-2624 when upgrading to gzip 1.3.13
# Drop patch for CVE-2010-0001 when upgrading to gzip 1.4

# Drop this .bbappend when upgrading to gzip 1.4 or newer, which will
# include these patches:
# http://scripts.mit.edu/trac/changeset/1433

PKGV .= "-0webos1"
EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BP}:"
SRC_URI += "file://gzip-cve-2009-2624.patch"
SRC_URI += "file://gzip-cve-2010-0001.patch"
