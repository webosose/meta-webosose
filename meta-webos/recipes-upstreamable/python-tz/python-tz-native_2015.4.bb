# Copyright (c) 2014-2015 LG Electronics, Inc.

SUMMARY = "pytz brings the Olson tz database into Python"

DESCRIPTION="This library allows accurate and cross platform timezone calculations using Python 2.4 or higher"

HOMEPAGE = "https://pypi.python.org/pypi/pytz/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=22b38951eb857cf285a4560a914b7cd6"

PR = "r1"

SRC_URI = "https://pypi.python.org/packages/source/p/pytz/pytz-${PV}.tar.gz \
    file://fix.for.tzdata-2015e.patch \
"
SRC_URI[md5sum] = "417a47b1c432d90333e42084a605d3d8"
SRC_URI[sha256sum] = "c4ee70cb407f9284517ac368f121cf0796a7134b961e53d9daf1aaae8f44fb90"

S="${WORKDIR}/pytz-${PV}"

inherit native
inherit setuptools
