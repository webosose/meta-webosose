# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Backport
# https://lists.openembedded.org/g/openembedded-core/message/186317

# Prevent installing copy of tzdata based on tzdata installation on the build host
# It doesn't install tzdata if one of the following files exist on the host:
# /usr/share/zoneinfo/UTC /usr/share/zoneinfo/GMT /usr/share/lib/zoneinfo/UTC /usr/share/lib/zoneinfo/GMT /usr/lib/zoneinfo/UTC /usr/lib/zoneinfo/GMT
# otherwise "/usr/lib/tcl8.6/tzdata" is included in tcl package
EXTRA_OECONF += "--with-tzdata=no"
