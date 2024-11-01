# Copyright (c) 2023-2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos4"

PACKAGECONFIG[linguist] = "-DFEATURE_linguist=ON,-DFEATURE_linguist=OFF"
# lrelease is being used by webos_qt_localization.bbclass
PACKAGECONFIG:append:class-native = " linguist"

# Disable the clang workaround which causes http://gecko.lge.com:8000/Errors/Details/1006441
# Also see https://codereview.qt-project.org/c/qt/qttools/+/589157
PACKAGECONFIG[no-clang-workaround] = "-DQT_NO_FIND_PACKAGE_CLANG_WORKAROUND=ON,-DQT_NO_FIND_PACKAGE_CLANG_WORKAROUND=OFF"
PACKAGECONFIG:append = " no-clang-workaround"
