# Copyright (c) 2012-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

# We don't want bash to ever be /bin/sh: our upstart conf files don't work when
# it is -- apparently it handles quotes on the command line differently from
# the ash supplied by busybox. Prevent the /bin/sh symlink from pointing to bash
# by decreasing its priority.
# busybox's /bin/sh has 50
ALTERNATIVE_PRIORITY[sh] = "40"

# Update sh target, because it's renamed now
ALTERNATIVE_TARGET[sh] = "${base_bindir}/bash.bash"

# Use u-a for /bin/bash binary, because our busybox defconfig enables bash now,
# but again use lower priority than busybox applet
ALTERNATIVE_${PN} += "bash"
ALTERNATIVE_LINK_NAME[bash] = "${base_bindir}/bash"
ALTERNATIVE_PRIORITY[bash] = "40"
