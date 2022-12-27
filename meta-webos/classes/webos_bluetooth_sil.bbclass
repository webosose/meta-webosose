# Copyright (c) 2014-2023 LG Electronics, Inc.
#
#
# This class is to be inherited by the recipe for every component that installs
# implements a webOS Bluetooth SIL
#


DEPENDS += "bluetooth-sil-api"

FILES:${PN} += "${libdir}/bluetooth-sils"
