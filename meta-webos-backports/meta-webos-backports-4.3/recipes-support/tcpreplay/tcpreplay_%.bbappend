# Backport changes from:
# https://git.openembedded.org/meta-openembedded/commit/?id=b74b10e31620f90b92979e2f1516bfbc8d051ec2
# https://lists.openembedded.org/g/openembedded-devel/message/104700
# https://lists.openembedded.org/g/openembedded-devel/message/104725

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-configure.ac-unify-search-dirs-for-pcap-and-add-lib3.patch"
