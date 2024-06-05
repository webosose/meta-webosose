# Copyright (c) 2024 LG Electronics, Inc.

# Define webos headless profile features
require conf/distro/include/features/webos-features-headless.inc

WEBOS_DISTRO_NAME_SUFFIX = " Headless"

# Increment when features are changed
IMAGE_PR_SUFFIX = "headless1${_pr_suffix}"
