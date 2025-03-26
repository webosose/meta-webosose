# Copyright (c) 2024-2025 LG Electronics, Inc.

# Inherit webos headlesss profile
require conf/distro/include/profile/webos-headless.inc

# Define webos qtless profile features
require conf/distro/include/features/webos-features-qtless.inc

WEBOS_DISTRO_NAME_SUFFIX = " Qt-less"

# Increment when features are changed
IMAGE_PR_SUFFIX = "qtless1${_pr_suffix}"
