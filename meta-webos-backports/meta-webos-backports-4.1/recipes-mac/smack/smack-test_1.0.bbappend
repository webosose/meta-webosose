FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
RDEPENDS:${PN} = "smack python3-core mmap-smack-test tcp-smack-test udp-smack-test"
