# Copyright (c) 2018-2022 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi5"

DEPENDS:append:rpi = " webos-wayland-extensions"

PACKAGECONFIG:append:rpi = " kms wayland"

PACKAGECONFIG:remove:rpi = "faad"
