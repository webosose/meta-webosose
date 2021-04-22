# Copyright (c) 2021 LG Electronics, Inc.

SUMMARY = "Python @deprecated decorator to deprecate old python classes, functions or methods."
HOMEPAGE = "https://github.com/tantale/deprecated"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=44288e26f4896bdab14072d4fa35ff01"

PYPI_PACKAGE = "Deprecated"

inherit pypi setuptools3

SRC_URI[sha256sum] = "6d2de2de7931a968874481ef30208fd4e08da39177d61d3d4ebdf4366e7dbca1"

RDEPENDS_${PN} = "${PYTHON_PN}-wrapt"

BBCLASSEXTEND = "native nativesdk"

