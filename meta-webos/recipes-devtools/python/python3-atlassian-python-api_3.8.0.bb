# Copyright (c) 2021-2024 LG Electronics, Inc.

SUMMARY = "Python Atlassian REST API Wrapper"
HOMEPAGE = "https://github.com/atlassian-api/atlassian-python-api"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c0cc649c1397d72ecd022073599b8a41"

PYPI_PACKAGE = "atlassian-python-api"

inherit pypi setuptools3

SRC_URI[sha256sum] = "7ef384a91a790c807336e2bd6b7554284691aadd6d7413d199baf752dd84c53e"

RDEPENDS:${PN} = "${PYTHON_PN}-requests \
                  ${PYTHON_PN}-oauthlib \
                  ${PYTHON_PN}-urllib3 \
                  ${PYTHON_PN}-certifi \
                  ${PYTHON_PN}-requests-oauthlib \
                  ${PYTHON_PN}-six \
                  ${PYTHON_PN}-deprecated \
"

BBCLASSEXTEND = "native nativesdk"
