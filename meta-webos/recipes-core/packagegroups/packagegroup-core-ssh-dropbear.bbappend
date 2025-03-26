# Copyright (c) 2024-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Undo:
# https://git.openembedded.org/openembedded-core/commit/?id=a98188e83b2c027d99cc38e3367e1ec2a98efbb0
# https://git.openembedded.org/openembedded-core/commit/?h=dunfell&id=93796b2787c410385d3176495e5307327449d2f7
# just use -O when using new openssh-9 and want to scp from image with dropbear
RRECOMMENDS:${PN}:remove = "openssh-sftp-server"
