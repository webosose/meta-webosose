# Copyright (c) 2017-2018 LG Electronics, Inc.

EXTENDPRAUTO_append_rpi = "webosrpi3"

DEPENDS_append_rpi = " libdrm omx-components userland"

WEBOS_VERSION_rpi = "1.0.0-1_716e1c68be7b5e6c5fa4bf9fcdc75412bd2ad2cb"

COMPATIBLE_MACHINE_rpi = "(^raspberrypi3$)"

EXTRA_OECMAKE_append_rpi = " -DUSE_SVP_SET=FALSE -DOMX_SKIP64BIT=TRUE"

# raspberrypi3-64 uses raspberrypi3 MACHINEOVERRIDE as well, we need to disable it explicitly
# until the build is fixed, see remaining issues in:
# http://caprica.lgsvl.com:8080/Errors/Details/834865
COMPATIBLE_MACHINE_raspberrypi3-64 = "^$"
