# Copyright (c) 2012-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

# We don't want bash to ever be /bin/sh: our upstart conf files don't work when
# it is -- apparently it handles quotes on the command line differently from
# the ash supplied by busybox. Prevent the /bin/sh symlink from pointing to bash
# by decreasing its priority.
# busybox's /bin/sh has 50
ALTERNATIVE_PRIORITY[sh] = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', '40', '100', d)}"

# Update sh target, because it's renamed now
ALTERNATIVE_TARGET[sh] = "${base_bindir}/bash.bash"

# Use u-a for /bin/bash binary, because our busybox defconfig enables bash now,
# but again use lower priority than busybox applet
ALTERNATIVE_${PN} += "bash"
ALTERNATIVE_LINK_NAME[bash] = "${base_bindir}/bash"
ALTERNATIVE_PRIORITY[bash] = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', '40', '100', d)}"

# We need to revert changes from
# http://git.openembedded.org/openembedded-core/commit/?h=rocko&id=70e544452b6825686f06484d994936ded677825f
# because as described in:
# http://lists.openembedded.org/pipermail/openembedded-commits/2017-November/215520.html
# it doesn't work when GLIBC_GENERATE_LOCALES are restricted like they are in our builds:
# meta-webos/conf/distro/include/webos-toolchain.inc:GLIBC_GENERATE_LOCALES = "en_US.UTF-8"
RDEPENDS_${PN}-ptest_remove_libc-glibc = "locale-base-fr-fr locale-base-de-de locale-base-fr-fr.iso-8859-1 locale-base-zh-hk.big5-hkscs"
