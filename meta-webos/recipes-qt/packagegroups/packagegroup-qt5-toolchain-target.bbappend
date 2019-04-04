# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# Remove the dependencies on recipes which are blacklisted in webOS
RDEPENDS_${PN}_remove = " \
    qt3d-dev \
    qt3d-mkspecs \
    qt3d-qmlplugins \
    qtconnectivity-dev \
    qtconnectivity-mkspecs \
    qtconnectivity-qmlplugins \
    qtlocation-dev \
    qtlocation-mkspecs \
    qtlocation-plugins \
    qtlocation-qmlplugins \
    qtmqtt-dev \
    qtmqtt-mkspecs \
    qtscript-dev \
    qtscript-mkspecs \
    qtsystems-dev \
    qtsystems-mkspecs \
    qtsystems-qmlplugins \
    qttools-dev \
    qttools-mkspecs \
    qttools-staticdev \
    qttools-tools \
    qtwebkit-dev \
"
RRECOMMENDS_${PN}_remove = " \
    qttools-plugins \
"
