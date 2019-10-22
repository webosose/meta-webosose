# Copyright (c) 2019-2020 LG Electronics, Inc.

# security.cfg - enable security features. Should be applied to default configuration.
SRC_URI_append = " \
    file://governor.cfg \
    file://ntfs.cfg \
    file://zram.cfg \
    file://security.cfg \
"
