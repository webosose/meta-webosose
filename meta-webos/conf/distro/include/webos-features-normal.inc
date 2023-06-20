# Copyright (c) 2023 LG Electronics, Inc.

require conf/distro/include/webos-features-minimal.inc

# Addition/removal distro features

# Includes AI Framework. You can specify the packages to be installed using the following options:
# aiframework: If this feature is specified, tensorflow-lite, opencv, opencl, edgeai-vision will be installed.
# armnn: Arm's ML SDKs arm-compute-library and armnn are installed. Along with this, the TFLite armnn-delegate for TFLite is installed.
# gpu-delegate: If the installation machine supports gpu acceleration, TFLite will build with the gpu delegate function.
# auto-acceleration: When this feature is added, the tflite-auto-delegation feature is enabled.
# edgetpu: Install libraries for Google Coral Edge TPU.
# ml-library-size-reduction: If the image does not have enough space to install all of the above ML libraries,
# you can reduce the size of the installed library by adding this option. If this option is specified,
# the examples and test codes are excluded from the installation.
WEBOS_DISTRO_FEATURES_AI_FRAMEWORK = "aiframework gpu-delegate auto-acceleration"
WEBOS_DISTRO_FEATURES_AI_FRAMEWORK_EXTENDED = "edgetpu"

DISTRO_FEATURES:append = "\
  ${WEBOS_DISTRO_FEATURES_AI_FRAMEWORK} \
  ${WEBOS_DISTRO_FEATURES_AI_FRAMEWORK_EXTENDED} \
  virtualization \
"
