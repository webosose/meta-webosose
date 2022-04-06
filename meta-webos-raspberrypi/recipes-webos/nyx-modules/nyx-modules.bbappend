# Copyright (c) 2020-2022 LG Electronics, Inc.

EXTENDPRAUTO:append:rpi = "webosrpi6"

DEPENDS:append:rpi = " nmeaparser"

RDEPENDS:${PN}:append:rpi = " libcec-examples"

WEBOS_VERSION:rpi = "7.1.0-20_7927148603cb9869421a7a3c35eef273cf085b21"

NYX_MODULES_REQUIRED:append:rpi = "NYXMOD_OW_GPS;NYXMOD_OW_CEC;"
