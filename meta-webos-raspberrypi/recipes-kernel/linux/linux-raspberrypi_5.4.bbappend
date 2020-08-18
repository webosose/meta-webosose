# Copyright (c) 2020 LG Electronics, Inc.

# security.cfg - enable security features. Should be applied to default configuration.
SRC_URI_append = " \
    file://0001-bcm2835-v4l2-codec-fix-vchiq-mmal-renable.patch \
    file://governor.cfg \
    file://ntfs.cfg \
    file://zram.cfg \
    file://security.cfg \
"

# ERROR: Feature 'cfg/virtio.scc' not found, this will cause configuration failures.
# enabled in:
# http://git.yoctoproject.org/cgit/cgit.cgi/meta-virtualization/commit/?id=21d8bcdb791a1ea766a3e7e7663c7b6d49bc861a
# but not available in 5.4 linux-raspberryp
KERNEL_FEATURES_remove = "cfg/virtio.scc"
