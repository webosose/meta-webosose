# Backport changes from:
# https://git.openembedded.org/meta-openembedded/commit/?id=b3f923e741ea53e95263095d3b1742743bd4eb8f
# https://lists.openembedded.org/g/openembedded-devel/message/104697
# https://lists.openembedded.org/g/openembedded-devel/message/104722

EXTRA_OESCONS += "--use-hardware-crc32=${@bb.utils.contains('TUNE_FEATURES', 'crc', 'on', 'off', d)}"
