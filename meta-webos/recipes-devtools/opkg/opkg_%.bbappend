# Copyright (c) 2017-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos6"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append = " \
    file://0001-Load-installed-files-before-update-file-ownership.patch \
    file://0002-Sort-opkg-list-file-content-to-speed-package-removal.patch \
    file://0003-Add-symlink-path-validity.patch \
"
SRC_URI:append:class-target = " \
    file://0004-Mask-S_ISUID-and-S_ISGID-permission-bits.patch \
"

do_install:append() {
    if ! grep status_file ${D}${sysconfdir}/opkg/opkg.conf; then
cat << EOF >> ${D}${sysconfdir}/opkg/opkg.conf

# From opkg-3.0.0 NEWS
# Add config options 'info_dir' and 'status_file'. Together with the config
# option 'lists_dir' which existed previously, these options replace the
# configure script argument '--with-opkglibdir', allowing the paths to the the
# data files and directories to be specified at run-time and with better
# granularity.
# libopkg/opkg_conf.h:#define OPKG_CONF_DEFAULT_INFO_DIR      "/var/lib/opkg/info"
# libopkg/opkg_conf.h:#define OPKG_CONF_DEFAULT_STATUS_FILE   "/var/lib/opkg/status"
option info_dir    /usr/lib/opkg/info
option status_file /usr/lib/opkg/status
option lock_file   /var/run/opkg.lock
EOF
    fi
}
