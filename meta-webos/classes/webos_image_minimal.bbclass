# Copyright (c) 2023 LG Electronics, Inc.

inherit webos_image_core-boot

# Increse when features are changed
_pr_suffix = "minimal2"

# Addition/removal image features
IMAGE_FEATURES += "webos-extract-ls2-api"
