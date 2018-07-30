# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosros21"

RDEPENDS_${PN}_append = " \
    com.webos.service.rosbridge \
    packagegroup-ros2-world \
"
