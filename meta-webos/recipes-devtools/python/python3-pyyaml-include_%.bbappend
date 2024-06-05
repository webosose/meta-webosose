# Copyright (c) 2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# python3-unittest-automake-output isn't in oe-core/kirkstone, it was added only in mickledore
# reported in: https://lists.openembedded.org/g/openembedded-devel/message/110191
# This is only temporary until the fix is merged in meta-oe/kirkstone:
# https://lists.openembedded.org/g/openembedded-devel/message/110196
RDEPENDS:${PN}-ptest:remove = "python3-unittest-automake-output"
