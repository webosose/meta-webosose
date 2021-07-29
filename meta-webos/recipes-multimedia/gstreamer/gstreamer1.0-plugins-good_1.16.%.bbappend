# Copyright (c) 2018-2020 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

PACKAGECONFIG:remove = "gtk"

# backport from meta-qt5/master 7f324f11bbbfe326cbb90edc8efbbe3f4fcc0874
PACKAGECONFIG[qt5] = '-Dqt5=enabled,-Dqt5=disabled,gstreamer1.0-plugins-base qtbase qtdeclarative qtbase-native'
