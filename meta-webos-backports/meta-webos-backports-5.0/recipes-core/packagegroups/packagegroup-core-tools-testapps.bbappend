# Copyright (c) 2024-2025 LG Electronics, Inc.

# skipped in layer.conf and removed from oe-core in nanbield:
# https://git.openembedded.org/openembedded-core/commit/?h=nanbield&id=8baaf94d200f5355791ecd980727698b1ab0e539
RDEPENDS:${PN}:remove = "rust-hello-world"
