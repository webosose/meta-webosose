# Copyright (c) 2018-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# until pseudo is completely fixed
# PLAT-48507 pseudo: random package_qa failures
INSANE_SKIP_${PN}-mkspecs += "host-user-contaminated"
