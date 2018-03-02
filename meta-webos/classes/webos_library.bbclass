# Copyright (c) 2012-2013 LG Electronics, Inc.
#
# webos_library
#
# This class is to be inherited by the recipe for every component that installs a
# library.
#

# We expect all libraries will use pkgconfig when building and will install
# pkgconfig (.pc) files.
inherit webos_pkgconfig
