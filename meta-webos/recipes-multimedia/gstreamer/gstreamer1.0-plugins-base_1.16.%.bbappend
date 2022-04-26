# Copyright (c) 2018-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# opengl causes undeclared dependency on libgudev
# ERROR: gstreamer1.0-plugins-base-1.14.0-r0 do_package_qa: QA Issue: libgstgl-1.0 rdepends on libgudev, but it isn't a build dependency, missing libgudev in DEPENDS or PACKAGECONFIG? [build-deps]
# ERROR: gstreamer1.0-plugins-base-1.14.0-r0 do_package_qa: QA Issue: gstreamer1.0-plugins-base-opengl rdepends on libgudev, but it isn't a build dependency, missing libgudev in DEPENDS or PACKAGECONFIG? [build-deps]
# This problem doesn't exist with newer Yocto with RSS
DEPENDS += "libgudev"
