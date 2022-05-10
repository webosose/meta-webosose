# From https://lists.openembedded.org/g/openembedded-devel/message/97027
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += " \
    file://0001-chunked-update_into-5419.patch \
    file://0002-chunking-didn-t-actually-work-5499.patch \
    file://0003-correct-buffer-overflows-cause-by-integer-overflow-i.patch \
"
