# Copyright (c) 2020 LG Electronics, Inc.

# security.cfg - enable security features. Should be applied to default configuration.
SRC_URI:append = " \
    file://0001-bcm2835-v4l2-codec-fix-vchiq-mmal-renable.patch \
    file://governor.cfg \
    file://ntfs.cfg \
    file://zram.cfg \
    file://security.cfg \
"
