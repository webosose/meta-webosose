# Copyright (c) 2023 LG Electronics, Inc.

inherit webos_image_minimal

# Increse when features are changed
_pr_suffix = "normal1"

# Addition/removal image features
IMAGE_FEATURES += "webos-systemapps webos-testapps webos-extended"
