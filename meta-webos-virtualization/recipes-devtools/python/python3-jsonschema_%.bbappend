# Copyright (c) 2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosvirt1"

# remove optional dependencies that has GPL3 license
PACKAGECONFIG:remove = "format nongpl"
