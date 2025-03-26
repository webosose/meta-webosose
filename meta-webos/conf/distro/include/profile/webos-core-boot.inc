# Copyright (c) 2023-2025 LG Electronics, Inc.

# Define webos core-boot profile features
require conf/distro/include/features/webos-features-core-boot.inc

WEBOS_DISTRO_NAME_SUFFIX = " Core boot"

# Increment when features are changed
IMAGE_PR_SUFFIX = "coreboot1${_pr_suffix}"
