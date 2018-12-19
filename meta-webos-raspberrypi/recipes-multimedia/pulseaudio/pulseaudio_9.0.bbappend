# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi1"

DEPENDS += "sbc dbus bluez5"

PACKAGECONFIG[bluez5] = "--enable-bluez5,bluez5 sbc"
RDEPENDS_pulseaudio-server_append += "\
    pulseaudio-lib-bluez5-util \
    pulseaudio-module-bluetooth-discover \
    pulseaudio-module-bluetooth-policy \
    pulseaudio-module-bluez5-device \
    pulseaudio-module-bluez5-discover \
"
