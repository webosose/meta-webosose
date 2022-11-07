# Copyright (c) 2020-2022 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi7"

RDEPENDS:${PN}:append:rpi = " libcec-examples"

NYX_MODULES_REQUIRED:append:rpi = "NYXMOD_OW_CEC;"
