# Copyright (c) 2019-2020 LG Electronics, Inc.

# backport of https://github.com/agherzan/meta-raspberrypi/pull/514
LINUX_VERSION = "4.19.80"
SRCREV = "3492a1b003494535eb1b17aa7f258469036b1de7"

# security.cfg - enable security features. Should be applied to default configuration.
SRC_URI_append = " \
    file://governor.cfg \
    file://ntfs.cfg \
    file://zram.cfg \
    file://security.cfg \
"
