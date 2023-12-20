# Copyright (c) 2012-2023 LG Electronics, Inc.

require conf/distro/include/webos.inc

# Increament when features are changed
_pr_suffix .= ".0"

# Keep in sync with setting in oe-core/meta/conf/distro/include/default-distrovars.inc
# TODO: Maybe change this to use "_remove" when it's introduced in bitbake

# Split some features to separate variables
# Remove argp (not used in OE anymore) and x11 (not needed for webOS OSE)
WEBOS_DISTRO_FEATURES_DEFAULT = ""
WEBOS_DISTRO_FEATURES_OPENGL = "opengl"
WEBOS_DISTRO_FEATURES_WINDOW_SYSTEM = ""
# Remove pci pcmcia
WEBOS_DISTRO_FEATURES_BUSES = ""
WEBOS_DISTRO_FEATURES_USB = ""
WEBOS_DISTRO_FEATURES_CONNECTIVITY = "wifi"
WEBOS_DISTRO_FEATURES_NETWORKING = ""

WEBOS_DISTRO_FEATURES_INIT = "systemd"
WEBOS_DISTRO_FEATURES_INIT_CONSIDERED = "sysvinit"
# Our gstreamer recipes and forks didn't apply the fixes for introspection.m4 and fail with:
# | make[4]: *** No rule to make target `/usr/bin/g-ir-scanner-wrapper', needed by `Gst-1.0.gir'.  Stop.
WEBOS_DISTRO_FEATURES_INIT_CONSIDERED += "gobject-introspection-data"

DISTRO_FEATURES_BACKFILL_CONSIDERED += "${WEBOS_DISTRO_FEATURES_INIT_CONSIDERED}"

WEBOS_DISTRO_FEATURES_LD = "ld-is-gold"

WEBOS_DISTRO_FEATURES_TEST = "ptest"

DISTRO_FEATURES = " \
  ${WEBOS_DISTRO_FEATURES_DEFAULT} \
  ${WEBOS_DISTRO_FEATURES_OPENGL} \
  ${WEBOS_DISTRO_FEATURES_WINDOW_SYSTEM} \
  ${WEBOS_DISTRO_FEATURES_BUSES} \
  ${WEBOS_DISTRO_FEATURES_USB} \
  ${WEBOS_DISTRO_FEATURES_CONNECTIVITY} \
  ${WEBOS_DISTRO_FEATURES_NETWORKING} \
  ${WEBOS_DISTRO_FEATURES_LD} \
  ${WEBOS_DISTRO_FEATURES_INIT} \
  ${WEBOS_DISTRO_FEATURES_TEST} \
"

# We won't use webos's initscripts for core-boot profile
VIRTUAL-RUNTIME_initscripts = ""
PREFERRED_RPROVIDER_initd-functions = ""

# Additional image features
IMAGE_FEATURES += ""

# Do not install recommended packages as default
NO_RECOMMENDATIONS ?= "1"
