# Copyright (c) 2020-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosrpi5"

DEPENDS:append = " nmeaparser"

RDEPENDS:${PN} += "libcec-tools"

WEBOS_VERSION = "7.1.0-20_7927148603cb9869421a7a3c35eef273cf085b21"

NYX_MODULES_REQUIRED:append = "NYXMOD_OW_GPS;NYXMOD_OW_CEC;"
