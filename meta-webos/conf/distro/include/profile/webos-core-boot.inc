# Copyright (c) 2023 LG Electronics, Inc.

# Define webos core-boot profile features
require conf/distro/include/features/webos-features-core-boot.inc

WEBOS_DISTRO_NAME_SUFFIX = " Core boot"

# Increament when features are changed
IMAGE_PR_SUFFIX = "coreboot1${_pr_suffix}"