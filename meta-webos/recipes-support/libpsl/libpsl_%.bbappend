# Copyright (c) 2020 LG Electronics, Inc.

# You don't need to change this value when you're changing just the RDEPENDS_${PN} variable.
EXTENDPRAUTO_append = "webos1"

# Remove GPL 3.0 components (libidn2 , libunistring). Need to check for developer, this componenet used by gstreamer1.0-plugins-good
DEPENDS_remove = "libidn2"
