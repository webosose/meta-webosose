# Backport https://git.openembedded.org/openembedded-core/commit/?h=kirkstone&id=d5c7ec0be32aa75fa7973840adf5251d22018766
# which is applied in nanbield and backported to LTS kirkstone, but missing in langdale and mickledore
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += " \
    file://0001-Remove-deprecated-add-needed-linker-flag.patch \
    file://0002-Add-T-workaround-for-GNU-ld-2.36.patch \
    file://0003-Set-LC_ALL-C-to-force-English-output-from-ld.patch \
    file://0004-LLD-fix-detection-and-remove-not-needed-workarounds.patch \
    file://0005-Revamp-efi_well_known_-variable-handling.patch \
"
LDFLAGS:remove = "-fuse-ld=bfd"
