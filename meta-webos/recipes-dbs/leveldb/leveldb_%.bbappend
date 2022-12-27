# Copyright (c) 2017-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

PACKAGECONFIG:append = " snappy"

# Needed for db8-native
BBCLASSEXTEND = "native"
