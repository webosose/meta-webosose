# Copyright (c) 2020-2022 LG Electronics, Inc.

# IMAGE_FEATURES controls the contents of webOS IoT images

FEATURE_PACKAGES_webos-iot = " packagegroup-webos-iot"
WEBOS_IMAGE_BASE_INSTALL:append = "${FEATURE_PACKAGES_webos-iot}"

inherit webos_iot_core
