# Copyright (c) 2012-2013 LG Electronics, Inc.
#
# webos_component
#
# This class is to be inherited by the recipe for every component developed by
# Palm that has had any do_*() task code required for its standalone Ubuntu desktop
# build to work moved to be in its CMakeLists/Makefile and is able to support
# having the filesystem layout rooted at /opt/webos.
#

inherit webos_fs_layout

WEBOS_SYSTEM_BUS_SKIP_DO_TASKS = "1"
