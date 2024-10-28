# Copyright (c) 2023-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos3"

PACKAGECONFIG[linguist] = "-DFEATURE_linguist=ON,-DFEATURE_linguist=OFF"
# lrelease is being used by webos_qt_localization.bbclass
PACKAGECONFIG:append:class-native = " linguist"
