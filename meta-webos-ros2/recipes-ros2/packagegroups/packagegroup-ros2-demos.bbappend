# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosros21"

# ros2-demo-nodes-cpp depends on blacklisted rclcpp
RDEPENDS_${PN}_remove_armv4 = "ros2-demo-nodes-cpp"
RDEPENDS_${PN}_remove_armv5 = "ros2-demo-nodes-cpp"
