# Copyright (c) 2020 LG Electronics, Inc.

# You don't need to change this value when you're changing just the RDEPENDS_${PN} variable.
EXTENDPRAUTO_append = "webos1"

#Fix build error(update-alternatives error)
do_install_append() {
  mv ${D}${bindir}/df ${D}${bindir}/df.${BPN}
}

# Reference from http://gpro.lge.com/c/webos-pro/meta-lg-webos/+/198815
# Modify u-a for /usr/bin/df binary, because our busybox defconfig has df applet enabled,
# and busybox installs it to /bin/df and coreutils to /usr/bin/df
ALTERNATIVE_LINK_NAME[df] = "${base_bindir}/df"
ALTERNATIVE_TARGET[df] = "${bindir}/df.${BPN}"
