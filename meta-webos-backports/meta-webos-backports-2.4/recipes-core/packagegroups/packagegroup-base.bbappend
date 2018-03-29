# Copyright (c) 2018 LG Electronics, Inc.

# Backport a fix from:
# http://git.openembedded.org/openembedded-core/commit/?id=8b433f49c8ea153f75d986e5b9ad89dd3f625cba
# to prevent both bluez5 versions (5.41 from oe-core and 5.48 from meta-webos-backports-2.5) being
# pulled to dependency tree, because only the first one provides libasound-module-bluez
# ERROR: Multiple versions of bluez5 are due to be built (meta-webos-backports/meta-webos-backports-2.5/recipes-connectivity/bluez5/bluez5_5.48.bb oe-core/meta/recipes-connectivity/bluez5/bluez5_5.41.bb). Only one version of a given PN should be built in any given build. You likely need to set PREFERRED_VERSION_bluez5 to select the correct version or don't depend on multiple versions.

RDEPENDS_packagegroup-base-bluetooth_remove = "libasound-module-bluez"
RDEPENDS_packagegroup-base-bluetooth_append = "${@bb.utils.contains('BLUEZ', 'bluez4', ' libasound-module-bluez', '', d)}"
