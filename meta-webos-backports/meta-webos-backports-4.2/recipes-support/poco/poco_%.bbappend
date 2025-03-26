# Branches used in langdale, mickledore, nanbield were re-written in upstream :(, fixes sent to meta-oe:
# langdale: https://lists.openembedded.org/g/openembedded-devel/message/107533
# mickledore: https://lists.openembedded.org/g/openembedded-devel/message/107531
# merged in:
# https://git.openembedded.org/meta-openembedded/commit/?h=mickledore&id=b0d67900ae9e8911f734c25c0674fe55df8cd188
# nanbield: https://lists.openembedded.org/g/openembedded-devel/message/107532
# merged in:
# https://git.openembedded.org/meta-openembedded/commit/?h=nanbield&id=2da6e1b0e43a8993fd422fee3f83940100b59f4c
inherit fix_branch_name
FIX_BRANCH_TO = "nobranch=1"
