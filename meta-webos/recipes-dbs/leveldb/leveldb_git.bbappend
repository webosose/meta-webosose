# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# this might be autodetected from
# ./meta-webos/recipes-upstreamable/snappy/snappy_git.bb
# that's why it isn't in the upstream meta-oe recipe
DEPENDS += "snappy"

# Needed for db8-native
BBCLASSEXTEND = "native"
