# Copyright (c) 2021-2025 LG Electronics, Inc.

SUMMARY = "Python Atlassian REST API Wrapper"
HOMEPAGE = "https://github.com/atlassian-api/atlassian-python-api"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c0cc649c1397d72ecd022073599b8a41"

PYPI_PACKAGE = "atlassian-python-api"

inherit pypi setuptools3

PR = "r1"

SRC_URI[sha256sum] = "7ef384a91a790c807336e2bd6b7554284691aadd6d7413d199baf752dd84c53e"

RDEPENDS:${PN} = "python3-requests \
                  python3-oauthlib \
                  python3-urllib3 \
                  python3-certifi \
                  python3-requests-oauthlib \
                  python3-six \
                  python3-deprecated \
"

BBCLASSEXTEND = "native nativesdk"
