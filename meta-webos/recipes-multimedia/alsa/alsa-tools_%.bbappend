# Copyright (c) 2018-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# gtk+3 is blacklisted in our builds, but upstream recipe adds it because we have wayland in DISTRO_FEATURES
# so that gtk+3 should be buildable
PACKAGECONFIG:remove = "hdajackretask"
