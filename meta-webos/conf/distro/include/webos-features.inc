# Copyright (c) 2012-2019 LG Electronics, Inc.

# Keep in sync with setting in oe-core/meta/conf/distro/include/default-distrovars.inc
# TODO: Maybe change this to use "_remove" when it's introduced in bitbake

# Split some features to separate variables
# Remove argp (not used in OE anymore) and x11 (not needed for webOS OSE)
WEBOS_DISTRO_FEATURES_DEFAULT = "\
  alsa ext2 irda largefile xattr \
"
WEBOS_DISTRO_FEATURES_OPENGL = "opengl"
# Remove x11
WEBOS_DISTRO_FEATURES_WINDOW_SYSTEM = "wayland"
# Remove pci pcmcia
WEBOS_DISTRO_FEATURES_BUSES = ""
WEBOS_DISTRO_FEATURES_USB = "usbhost usbgadget"
WEBOS_DISTRO_FEATURES_CONNECTIVITY = "bluetooth wifi ipv6"
WEBOS_DISTRO_FEATURES_NETWORKING = "nfs zeroconf"

WEBOS_DISTRO_FEATURES_INIT = "systemd"
WEBOS_DISTRO_FEATURES_INIT_CONSIDERED = "sysvinit"
# Our gstreamer recipes and forks didn't apply the fixes for introspection.m4 and fail with:
# | make[4]: *** No rule to make target `/usr/bin/g-ir-scanner-wrapper', needed by `Gst-1.0.gir'.  Stop.
WEBOS_DISTRO_FEATURES_INIT_CONSIDERED += "gobject-introspection-data"

DISTRO_FEATURES_BACKFILL_CONSIDERED += "${WEBOS_DISTRO_FEATURES_INIT_CONSIDERED}"

WEBOS_DISTRO_FEATURES_LD = "ld-is-gold"
DISTRO_FEATURES_BACKFILL:append = " ${WEBOS_DISTRO_FEATURES_LD}"
WEBOS_DISTRO_FEATURES_LD_CONSIDERED ?= ""

DISTRO_FEATURES_BACKFILL_CONSIDERED += "${WEBOS_DISTRO_FEATURES_LD_CONSIDERED}"

WEBOS_DISTRO_FEATURES_TEST = "ptest"

DISTRO_FEATURES = "\
  ${WEBOS_DISTRO_FEATURES_DEFAULT} \
  ${WEBOS_DISTRO_FEATURES_OPENGL} \
  ${WEBOS_DISTRO_FEATURES_WINDOW_SYSTEM} \
  ${WEBOS_DISTRO_FEATURES_BUSES} \
  ${WEBOS_DISTRO_FEATURES_USB} \
  ${WEBOS_DISTRO_FEATURES_CONNECTIVITY} \
  ${WEBOS_DISTRO_FEATURES_NETWORKING} \
  ${WEBOS_DISTRO_FEATURES_LIBC} \
  ${WEBOS_DISTRO_FEATURES_INIT} \
  ${WEBOS_DISTRO_FEATURES_TEST} \
  virtualization \
"
