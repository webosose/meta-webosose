# Copyright (c) 2024 LG Electronics, Inc.

require conf/distro/include/features/webos-features-headless.inc

# Increment when features are changed
_pr_suffix .= ".1"

# Addition/removal distro features
WEBOS_DISTRO_FEATURES_QT = ""

# avoid qt6-cmake inherit - migrate this to inherit_defer after upgrading to scarthgap with:
# https://git.openembedded.org/openembedded-core/commit/?h=scarthgap&id=451363438d38bd4552d5bcec4a92332f5819a5d4
# https://git.openembedded.org/bitbake/commit/?h=2.8&id=5c2e840eafeba1f0f754c226b87bfb674f7bea29
# EXTRA_INHERIT:pn-luna-sysservice = ""
# And in this case removing the qt6-cmake inherit isn't enough, the luna-sysservice really depends on qtbase
# until http://clm.lge.com/issue/browse/WSAPI-3390. (Retiring wallpaper category is the key to make it Qt-less)
RDEPENDS:${PN}:remove:pn-packagegroup-core-boot = "luna-sysservice"
