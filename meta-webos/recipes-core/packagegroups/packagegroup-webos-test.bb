# Copyright (c) 2014-2018 LG Electronics, Inc.

SUMMARY = "Components for testing added to webOS OSE -devel images"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS_${PN} variable.
PR = "r15"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

VIRTUAL-RUNTIME_umediaserver-python ?= "umediaserver-python"
VIRTUAL-RUNTIME_umediaserver-python_armv4 = ""
VIRTUAL-RUNTIME_umediaserver-python_armv5 = ""

# A group of Python 2 packages to develop and exercise tests for
# webOS OSE processes.
PYTHON = " \
    python \
    python-2to3 \
    python-audio \
    python-bsddb \
    python-codecs \
    python-compile \
    python-compiler \
    python-compression \
    python-core \
    python-crypt \
    python-ctypes \
    python-curses \
    python-datetime \
    python-db \
    python-debugger \
    python-difflib \
    python-distutils \
    python-email \
    python-fcntl \
    python-gdbm \
    python-hotshot \
    python-html \
    python-idle \
    python-image \
    python-io \
    python-json \
    python-lang \
    python-logging \
    python-mailbox \
    python-math \
    python-mime \
    python-misc \
    python-mmap \
    python-multiprocessing \
    python-netclient \
    python-netserver \
    python-numbers \
    python-pickle \
    python-pkgutil \
    python-pprint \
    python-profile \
    python-pydoc \
    python-re \
    python-resource \
    python-robotparser \
    python-setuptools \
    python-shell \
    python-smtpd \
    python-sqlite3 \
    python-sqlite3-tests \
    python-stringold \
    python-subprocess \
    python-syslog \
    python-terminal \
    python-tests \
    python-textutils \
    python-threading \
    python-tkinter \
    python-unittest \
    python-unixadmin \
    python-xml \
    python-xmlrpc \
    python-zlib \
"

# ptest's default approach to running a component's tests is 'make test'.
# glib-2.0 and dbus certainly assume this, and that's what their run-ptest
# scripts do. But they don't follow https://wiki.yoctoproject.org/wiki/Ptest
# and include an 'RDEPENDS_${PN}-ptest += "make"'. As we know at least two
# components need it, add make to the general dependencies for the packagegroup.
RDEPENDS_${PN} = "\
    db8-tests \
    glib-2.0-utils \
    iotop \
    ltp \
    make \
    ptest-runner \
    sam-tests \
    ${VIRTUAL-RUNTIME_umediaserver-python} \
    ${PYTHON} \
"
