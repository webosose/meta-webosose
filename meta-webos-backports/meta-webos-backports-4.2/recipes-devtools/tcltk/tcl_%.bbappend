# Copyright (c) 2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Backport
# https://git.openembedded.org/openembedded-core/commit/?id=3ace9fbfeb42ebf920812e3dd6d665b8b20a1ca0
# https://git.openembedded.org/openembedded-core/commit/?h=mickledore&id=d5a038b764720bd3267f258994371909f3f34ad6
# https://lists.openembedded.org/g/openembedded-core/message/186317

# Prevent installing copy of tzdata based on tzdata installation on the build host
# It doesn't install tzdata if one of the following files exist on the host:
# /usr/share/zoneinfo/UTC /usr/share/zoneinfo/GMT /usr/share/lib/zoneinfo/UTC /usr/share/lib/zoneinfo/GMT /usr/lib/zoneinfo/UTC /usr/lib/zoneinfo/GMT
# otherwise "/usr/lib/tcl8.6/tzdata" is included in tcl package
EXTRA_OECONF += "--with-tzdata=no"
