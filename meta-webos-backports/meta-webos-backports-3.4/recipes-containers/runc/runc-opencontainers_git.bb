include runc.inc

SRCREV = "86d83333d765f4535e4898d6778388dab715eb7c"
SRC_URI = " \
    git://github.com/opencontainers/runc;branch=release-1.0;protocol=https \
    file://0001-Makefile-respect-GOBUILDFLAGS-for-runc-and-remove-re.patch \
    "
RUNC_VERSION = "1.0.2"

CVE_PRODUCT = "runc"
