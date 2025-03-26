# rygel which with ./meta-webos-backports/meta-webos-backports-5.0/recipes-connectivity/rygel/rygel_%.bbappend requires x11
# in DISTRO_FEATURES which packagegroup-meta-multimedia doesn't respect until
# https://git.openembedded.org/meta-openembedded/commit/?id=23b62b3391f2e605f59e02abb3c99db2a7b82eb5
RDEPENDS:packagegroup-meta-multimedia-connectivity:remove = "rygel"
