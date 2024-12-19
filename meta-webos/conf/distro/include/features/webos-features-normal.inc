# Copyright (c) 2023-2024 LG Electronics, Inc.

require conf/distro/include/features/webos-features-minimal.inc

# Increment when features are changed
_pr_suffix .= ".4"

# Addition/removal distro features
WEBOS_DISTRO_FEATURES_EXTENDED += "virtualization"

# You can specify the packagegroups to be installed using the following options:
# webos-<feature>: If this feature is specified, packagegroup-webos-<feature> will be installed.
WEBOS_DISTRO_FEATURES_EXTENDED += "\
  webos-audio \
  webos-bluetooth \
  webos-camera \
  webos-connectivity \
  webos-diagnostics \
  webos-graphics-extended \
  webos-i18n \
  webos-media \
  webos-media-drm \
  webos-screencast \
"

# Includes AI Framework. You can specify the packages to be installed using the following options:
# webos-aiframework: If this feature is specified, tensorflow-lite, opencv, opencl, edgeai-vision will be installed.
# webos-armnn: Arm's ML SDKs arm-compute-library and armnn are installed. Along with this, the TFLite armnn-delegate for TFLite is installed.
# webos-gpu-delegate: If the installation machine supports gpu acceleration, TFLite will build with the gpu delegate function.
# webos-auto-acceleration: When this feature is added, the tflite-auto-delegation feature is enabled.
# webos-edgetpu: Install libraries for Google Coral Edge TPU.
# webos-ml-library-size-reduction: If the image does not have enough space to install all of the above ML libraries,
# you can reduce the size of the installed library by adding this option. If this option is specified,
# the examples and test codes are excluded from the installation.
WEBOS_DISTRO_FEATURES_AI_FRAMEWORK = "webos-aiframework webos-auto-acceleration webos-edgetpu webos-gpu-delegate"
WEBOS_DISTRO_FEATURES_EXTENDED += "${WEBOS_DISTRO_FEATURES_AI_FRAMEWORK}"

# Addition/removal image features
IMAGE_FEATURES += "${@ 'webos-systemapps webos-testapps webos-extended' if bb.data.inherits_class('webos_image', d) else ''}"

NO_RECOMMENDATIONS = "0"
