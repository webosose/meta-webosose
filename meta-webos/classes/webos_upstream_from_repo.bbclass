# Copyright (c) 2012-2025 LG Electronics, Inc.
#
# webos_upstream_from_repo
#
# This class is to be inherited by the recipe for every upstream component for
# which we maintain our own repo that has had any do_*() task code required for
# its standalone Ubuntu desktop build to work moved to be in its CMakeLists/Makefile
# and is able to support having the filesystem layout be rooted at /opt/webos.
#

inherit webos_fs_layout

WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"
