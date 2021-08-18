SUMMARY = "Multi-container orchestration for Docker"
HOMEPAGE = "https://www.docker.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=435b266b3899aa8a959f17d41c56def8"

SRC_URI += "file://0001-docker-compose-support-newer-minor-version-of-reques.patch"

inherit pypi setuptools3

SRC_URI[md5sum] = "a243f59b2d286c41ff6ed1c4d4b50996"
SRC_URI[sha256sum] = "68b07193755440d5f8d4f47e6f3484212afc255d5b785a81353ea1e9298c1c2c"

RDEPENDS_${PN} = "\
  ${PYTHON_PN}-cached-property \
  ${PYTHON_PN}-certifi \
  ${PYTHON_PN}-chardet \
  ${PYTHON_PN}-colorama \
  ${PYTHON_PN}-docker \
  ${PYTHON_PN}-docker-pycreds \
  ${PYTHON_PN}-dockerpty \
  ${PYTHON_PN}-docopt \
  ${PYTHON_PN}-idna \
  ${PYTHON_PN}-jsonschema \
  ${PYTHON_PN}-pyyaml \
  ${PYTHON_PN}-requests \
  ${PYTHON_PN}-six \
  ${PYTHON_PN}-terminal \
  ${PYTHON_PN}-texttable \
  ${PYTHON_PN}-urllib3 \
  ${PYTHON_PN}-websocket-client \
  ${PYTHON_PN}-fcntl \
  "
