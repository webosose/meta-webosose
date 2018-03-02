# Copyright (c) 2012-2013 LG Electronics, Inc.
#
# webos_program
#
# This class is to be inherited by the recipe for every component that installs
# a console program.
#

# We expect all programs will use pkgconfig when building.
inherit webos_pkgconfig
