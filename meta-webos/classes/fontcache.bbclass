# Copyright (c) 2018-2023 LG Electronics, Inc.
#
# Intercept the upstream fontcache.bbclass so that it doesn't add
# dependency on fontconfig-utils.
#

FONT_EXTRA_RDEPENDS = ""

require ${COREBASE}/meta/classes/fontcache.bbclass
