# Copyright (c) 2020-2022 LG Electronics, Inc.

# IMAGE_FEATURES controls the contents of webOS IoT images
#
# Available IMAGE_FEATURES:
#
# - webos-iot-core          - webOS IoT core components
# - webos-iot-extended      - webOS IoT components
#
FEATURE_PACKAGES_webos-iot-core = "packagegroup-webos-iot-core"
FEATURE_PACKAGES_webos-iot-devtools = "packagegroup-webos-iot-devtools"

WEBOS_IMAGE_BASE_INSTALL:remove = "${FEATURE_PACKAGES_webos-extended}"
WEBOS_IMAGE_BASE_INSTALL:append = "${FEATURE_PACKAGES_webos-iot-core}"

inherit webos_image
inherit webos_prerelease_dep
