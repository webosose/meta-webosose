# Copyright (c) 2012-2023 LG Electronics, Inc.

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

# Includes AI Framework. You can specify the packages to be installed using the following options:
# aiframework: If this feature is specified, tensorflow-lite, opencv, opencl, edgeai-vision will be installed.
# armnn: Arm's ML SDKs arm-compute-library and armnn are installed. Along with this, the TFLite armnn-delegate for TFLite is installed.
# gpu-delegate: If the installation machine supports gpu acceleration, TFLite will build with the gpu delegate function.
# auto-acceleration: When this feature is added, the tflite-auto-delegation feature is enabled.
# edgetpu: Install libraries for Google Coral Edge TPU.
# ml-library-size-reduction: If the image does not have enough space to install all of the above ML libraries,
# you can reduce the size of the installed library by adding this option. If this option is specified,
# the examples and test codes are excluded from the installation.
WEBOS_DISTRO_FEATURES_AI_FRAMEWORK = "aiframework armnn gpu-delegate auto-acceleration"
WEBOS_DISTRO_FEATURES_AI_FRAMEWORK_EXTENDED = "edgetpu"

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
  ${WEBOS_DISTRO_FEATURES_AI_FRAMEWORK} \
  ${WEBOS_DISTRO_FEATURES_AI_FRAMEWORK_EXTENDED} \
  virtualization \
"
