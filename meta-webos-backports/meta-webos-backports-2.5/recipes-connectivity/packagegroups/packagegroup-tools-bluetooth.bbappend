# Copyright (c) 2018 LG Electronics, Inc.

# Backport a fix from:
# http://git.openembedded.org/meta-openembedded/commit/?id=cf993a2c9e10dea286979850c4c5ecd751735ce8
# to prevent both bluez5 versions (5.41 from oe-core and 5.48 from meta-webos-backports-2.5) being
# pulled to dependency tree, because only the first one provides libasound-module-bluez
# ERROR: Multiple versions of bluez5 are due to be built (meta-webos-backports/meta-webos-backports-2.5/recipes-connectivity/bluez5/bluez5_5.48.bb oe-core/meta/recipes-connectivity/bluez5/bluez5_5.41.bb). Only one version of a given PN should be built in any given build. You likely need to set PREFERRED_VERSION_bluez5 to select the correct version or don't depend on multiple versions.

RDEPENDS_bluez5_remove = "libasound-module-bluez"
RDEPENDS_bluez4_append = "${@bb.utils.contains('DISTRO_FEATURES', 'alsa', ' libasound-module-bluez', '', d)}"
